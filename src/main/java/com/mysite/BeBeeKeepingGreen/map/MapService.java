package com.mysite.BeBeeKeepingGreen.map;

import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;

    public List<BeePlant> getConfirmedList(){
        return this.mapRepository.findAllByIsConfirmTrue();
    }

    public List<BeePlant> getWaitingList(){
        return this.mapRepository.findAllByIsConfirmFalse();
    }

    public BeePlant getPlant(int ID){
        System.out.println(ID);
        return this.mapRepository.findById(ID);
    }

    public void ackPlant(BeePlant bp){
        if (bp == null) {
            // 전달된 BeePlant 객체가 null인 경우 예외 처리 또는 로깅
            throw new IllegalArgumentException("전달된 BeePlant 객체가 null입니다.");
        }

        System.out.println("확인됨!!");
        bp.setConfirm(true);

        this.mapRepository.save(bp);
    }

    public void require(String plantLocation, String x, String y){
        BeePlant bp = new BeePlant();
        bp.setPlantLocation(plantLocation);
        bp.setX(x);
        bp.setY(y);
        bp.setConfirm(false);

        this.mapRepository.save(bp);
    }

    public int plantDensity(SiteUser owner){
        Double[] hive = new Double[2]; // 사용자 벌통 좌표
        int count = 0;
        hive[0] = Double.parseDouble(owner.getX());
        hive[1] = Double.parseDouble(owner.getY());
        List<BeePlant> checkPlantList = getConfirmedList();
        List<List<Double>> coords = new ArrayList<>(); // 비교할 밀원식물들의 좌표

        for (BeePlant bp : checkPlantList){
            List<Double> coord = new ArrayList<>();
            coord.add(Double.parseDouble(bp.getX()));
            coord.add(Double.parseDouble(bp.getY()));
            coords.add(coord);
        }
        for (List<Double> coord : coords){
            double d = distance(hive[0], hive[1], coord.get(0), coord.get(1));
            if(d<3.0){
                System.out.println(d);
                count++;
            }
        }
        // 반경 3km이내에 밀원식물이 얼마나 있는지 리턴
        // 10이상이면 2, 0이면 0, 그 사이면 1
        if(count >= 10) return 2;
        else if(count == 0) return 0;
        else return 1;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) { // 위도 경도로 거리 km환산
        final int R = 6371; // 지구의 반지름 (킬로미터)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance; // km로 리턴
    }
}
