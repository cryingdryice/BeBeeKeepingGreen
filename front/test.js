// TODO: devicePixelRatio for Retina

const canvasEl = document.querySelector("#gooey-canvas");
const svgEl = document.querySelector("#gooey-overlay");
const svgBallsEl = svgEl.querySelector(".balls");

const params = {
    pageContentMaxWidth: 800,
    btnPixelRadius: 43,
    widthPixel: 300,
    heightPixel: 250,
    clickPower: 0
}

const buttons = [
    {name: "user", pos: {x: 0, y: 0}},
    {name: "settings", pos: {x: 0, y: 0}},
    {name: "msg", pos: {x: 0, y: 0}},
]

const pointer = {
    x: -10, y: 0,
    tx: 0, ty: 0,
}

createOverlay();
resizeOverlay();

const uniforms = {
    timeLocation: null,
    dropsPosYLocation: null,
    dropsPosXLocation: null,
    pointerLocation: null,
    ratioLocation: null,
    clickLocation: null,
}
const gl = initShader();
setupAnimation();
resizeCanvas();


window.addEventListener("resize", () => {
    resizeOverlay();
    resizeCanvas();
});

gsap.ticker.add(onTick);

function onTick(t) {
    pointer.x += (pointer.tx - pointer.x) * .95;
    pointer.y += (pointer.ty - pointer.y) * .95;

    buttons.forEach(b => {
        gsap.set(b.el, {
            x: b.pos.x,
            y: b.pos.y
        });
    });
    renderShader(t);
}

// -----------------------------------------
// Canvas (Shader) part

function initShader() {
    const vsSource = document.getElementById("vertShader").innerHTML;
    const fsSource = document.getElementById("fragShader").innerHTML;

    const gl = canvasEl.getContext("webgl") || canvasEl.getContext("experimental-webgl");

    if (!gl) {
        alert("WebGL is not supported by your browser.");
    }

    function createShader(gl, sourceCode, type) {
        const shader = gl.createShader(type);
        gl.shaderSource(shader, sourceCode);
        gl.compileShader(shader);

        if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
            console.error("An error occurred compiling the shaders: " + gl.getShaderInfoLog(shader));
            gl.deleteShader(shader);
            return null;
        }

        return shader;
    }

    const vertexShader = createShader(gl, vsSource, gl.VERTEX_SHADER);
    const fragmentShader = createShader(gl, fsSource, gl.FRAGMENT_SHADER);

    function createShaderProgram(gl, vertexShader, fragmentShader) {
        const program = gl.createProgram();
        gl.attachShader(program, vertexShader);
        gl.attachShader(program, fragmentShader);
        gl.linkProgram(program);

        if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
            console.error("Unable to initialize the shader program: " + gl.getProgramInfoLog(program));
            return null;
        }

        return program;
    }

    const shaderProgram = createShaderProgram(gl, vertexShader, fragmentShader);
    const vertices = new Float32Array([-1., -1., 1., -1., -1., 1., 1., 1.]);

    const vertexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);

    gl.useProgram(shaderProgram);

    const positionLocation = gl.getAttribLocation(shaderProgram, "a_position");
    gl.enableVertexAttribArray(positionLocation);

    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

    uniforms.timeLocation = gl.getUniformLocation(shaderProgram, "u_time");
    uniforms.dropsPosXLocation = gl.getUniformLocation(shaderProgram, "u_drops_pos_x");
    uniforms.dropsPosYLocation = gl.getUniformLocation(shaderProgram, "u_drops_pos_y");
    uniforms.ratioLocation = gl.getUniformLocation(shaderProgram, "u_ratio");
    uniforms.pointerLocation = gl.getUniformLocation(shaderProgram, "u_pointer");
    uniforms.graphicVertRatioLocation = gl.getUniformLocation(shaderProgram, "u_max_y");
    uniforms.clickLocation = gl.getUniformLocation(shaderProgram, "u_click_t");

    return gl;
}

function resizeCanvas() {
    canvasEl.width = window.innerWidth;
    canvasEl.height = window.innerHeight;
    gl.uniform1f(uniforms.ratioLocation, window.innerWidth / window.innerHeight);
    gl.uniform1f(uniforms.graphicVertRatioLocation, params.heightPixel / window.innerHeight);
    gl.viewport(0, 0, canvasEl.width, canvasEl.height);
    console.log(params.heightPixel / window.innerHeight)

}


function renderShader(t) {

    gl.uniform1f(uniforms.timeLocation, t);

    const getShrX = (btn) => {
        return (params.xOffset + btn.pos.x) / window.innerWidth;
    }
    const getShrY = (btn) => {
        return btn.pos.y / window.innerHeight;
    }

    gl.uniform3f(uniforms.dropsPosXLocation, getShrX(buttons[0]), getShrX(buttons[1]), getShrX(buttons[2]));
    gl.uniform3f(uniforms.dropsPosYLocation, getShrY(buttons[0]), getShrY(buttons[1]), getShrY(buttons[2]));

    gl.clearColor(0.0, 0.0, 0.0, 1.0);
    gl.clear(gl.COLOR_BUFFER_BIT);
    gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);
}


// -----------------------------------------
// SVG (Clickable overlay) part

function createOverlay() {
    buttons.forEach(b => {
        const gEl = document.createElementNS("http://www.w3.org/2000/svg", "g");
        svgBallsEl.appendChild(gEl);
        const circleEl = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        circleEl.setAttribute("cx", "0");
        circleEl.setAttribute("cy", "0");
        circleEl.setAttribute("r", "" + params.btnPixelRadius);
        gsap.set(circleEl, {
            attr: {
                fill: "transparent",
            }
        })
        gEl.appendChild(circleEl);

        const iconEl = document.createElementNS("http://www.w3.org/2000/svg", "use");
        iconEl.setAttribute("href", "#icon-" + b.name);
        iconEl.setAttribute("x", -.5 * params.btnPixelRadius);
        iconEl.setAttribute("y", -.5 * params.btnPixelRadius);
        iconEl.setAttribute("width", params.btnPixelRadius);
        iconEl.setAttribute("height", params.btnPixelRadius);
        gEl.appendChild(iconEl);

        gEl.classList.add("menu-item");
        b.el = gEl;
    })
}

function resizeOverlay() {
    gsap.set(svgEl, {
        attr: {
            "viewBox": "0 0 " + window.innerWidth + " " + window.innerHeight
        }
    })

    params.xOffset = window.innerWidth - params.widthPixel;
    if (window.innerWidth > params.pageContentMaxWidth) {
        params.xOffset -= (.5 * (window.innerWidth - params.pageContentMaxWidth))
    }
    gsap.set(svgBallsEl, {
        x: params.xOffset
    })
}


function setupAnimation() {
    buttons.forEach((b, bIdx) => {
        b.yLoop = gsap.fromTo(b.pos, {
            y: params.btnPixelRadius + (bIdx === 1 ? 4 : 2) * params.btnPixelRadius,
        }, {
            duration: 2,
            y: params.heightPixel - (bIdx === 0 ? 2 : 0) * params.btnPixelRadius,
            ease: "sine.inOut",
            repeat: -1,
            yoyo: true
        })
            .progress(bIdx / buttons.length + .2 * Math.random());


        b.xLoop = gsap.fromTo(b.pos, {
            x: (bIdx / buttons.length) * params.widthPixel
        }, {
            duration: 2 + Math.random(),
            x: "+=" + (bIdx > 0 ? (-.12) : (.1)) * params.widthPixel,
            ease: "sine.inOut",
            repeat: -1,
            yoyo: true
        })
            .progress(Math.random());


        b.el.onclick = function () {
            gsap.timeline({
                onUpdate: () => {
                    gl.uniform1f(uniforms.clickLocation, params.clickPower);
                }
            })
                .to(params, {
                    duration: .1,
                    clickPower: .2,
                    ease: "power1.inOut"
                }, 0)
                .to(params, {
                    duration: .3,
                    clickPower: 0,
                    ease: "back(2).out"
                }, ">")
                .to(b.el, {
                    duration: .1,
                    opacity: .1,
                    ease: "power1.inOut"
                }, 0)
                .to(b.el, {
                    duration: .3,
                    opacity: 1,
                    ease: "power1.inOut"
                }, ">")
        }
    })

    // const maxDistance = 4 * params.btnPixelRadius;
    // window.addEventListener("mousemove", e => {
    //     pointer.tx = e.offsetX;
    //     pointer.ty = e.offsetY;
    //     gl.uniform2f(uniforms.pointerLocation, pointer.x / window.innerWidth, 1. - pointer.y / window.innerHeight);

    //     buttons.forEach(b => {
    //         const circleX = params.xOffset + gsap.getProperty(b.el, "x");
    //         const circleY = gsap.getProperty(b.el, "y");

    //         b.distance = Math.sqrt(Math.pow(pointer.tx - circleX, 2) + Math.pow(pointer.ty - circleY, 2)) / maxDistance;
    //         b.distance = Math.min(1, b.distance);

    //         const timeScale = 1.2 * Math.pow(b.distance, 2.);

    //         gsap.set(b.xLoop, {
    //             timeScale: timeScale
    //         })
    //         gsap.set(b.yLoop, {
    //             timeScale: timeScale
    //         })
    //     });
    // });
}