package com.senolkarakurt.advertisementservice.util;

import com.senolkarakurt.advertisementservice.model.House;
import com.senolkarakurt.advertisementservice.model.SummerHouse;
import com.senolkarakurt.advertisementservice.model.Villa;
import com.senolkarakurt.enums.CommercialStatus;
import com.senolkarakurt.enums.RecordStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateFakeData {

    public static List<House> getHouseList(){
        List<House> houseList = new ArrayList<>();
        House house1 = new House();
        house1.setWhichFloor(3);
        house1.setDetached(false);
        house1.setCommercialStatus(CommercialStatus.RENTABLE);
        house1.setRecordStatus(RecordStatus.ACTIVE);
        house1.setName("Sabancı Evi");
        house1.setDescription("Sabancı ailesine ait ev");
        house1.setSquareMeters(500);
        house1.setRoomCount(10);
        house1.setSaloonCount(5);
        house1.setFloorCount(3);
        house1.setPrice(BigDecimal.valueOf(10000000));
        houseList.add(house1);

        House house2 = new House();
        house2.setWhichFloor(4);
        house2.setDetached(false);
        house2.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        house2.setRecordStatus(RecordStatus.ACTIVE);
        house2.setName("Koç Evi");
        house2.setDescription("Koç ailesine ait ev");
        house2.setSquareMeters(600);
        house2.setRoomCount(12);
        house2.setSaloonCount(6);
        house2.setFloorCount(3);
        house2.setPrice(BigDecimal.valueOf(15000000));
        houseList.add(house2);

        House house3 = new House();
        house3.setDetached(true);
        house3.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        house3.setRecordStatus(RecordStatus.ACTIVE);
        house3.setName("Ülker Evi");
        house3.setDescription("Ülker ailesine ait ev");
        house3.setSquareMeters(800);
        house3.setRoomCount(16);
        house3.setSaloonCount(8);
        house3.setFloorCount(3);
        house3.setPrice(BigDecimal.valueOf(20000000));
        houseList.add(house3);

        return houseList;
    }

    public static List<Villa> getVillaList(){
        List<Villa> villaList = new ArrayList<>();

        Villa villa1 = new Villa();
        villa1.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        villa1.setRecordStatus(RecordStatus.ACTIVE);
        villa1.setName("Sabancı Villası");
        villa1.setDescription("Sabancı ailesine ait villa");
        villa1.setSquareMeters(1000);
        villa1.setRoomCount(20);
        villa1.setSaloonCount(8);
        villa1.setFloorCount(2);
        villa1.setPrice(BigDecimal.valueOf(30000000));
        villaList.add(villa1);

        Villa villa2 = new Villa();
        villa2.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        villa2.setRecordStatus(RecordStatus.ACTIVE);
        villa2.setName("Koç Villası");
        villa2.setDescription("Koç ailesine ait villa");
        villa2.setSquareMeters(1500);
        villa2.setRoomCount(28);
        villa2.setSaloonCount(10);
        villa2.setFloorCount(2);
        villa2.setPrice(BigDecimal.valueOf(38000000));
        villaList.add(villa2);

        Villa villa3 = new Villa();
        villa3.setCommercialStatus(CommercialStatus.RENTABLE);
        villa3.setRecordStatus(RecordStatus.ACTIVE);
        villa3.setName("Ülker Villası");
        villa3.setDescription("Ülker ailesine ait villa");
        villa3.setSquareMeters(2500);
        villa3.setRoomCount(40);
        villa3.setSaloonCount(20);
        villa3.setFloorCount(2);
        villa3.setPrice(BigDecimal.valueOf(58000000));
        villaList.add(villa3);

        return villaList;
    }

    public static List<SummerHouse> getSummerHouseList(){
        List<SummerHouse> summerHouseList = new ArrayList<>();

        SummerHouse summerHouse1 = new SummerHouse();
        summerHouse1.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        summerHouse1.setRecordStatus(RecordStatus.ACTIVE);
        summerHouse1.setName("Sabancı Yazlığı");
        summerHouse1.setDescription("Sabancı ailesine ait yazlık");
        summerHouse1.setSquareMeters(1000);
        summerHouse1.setRoomCount(20);
        summerHouse1.setSaloonCount(8);
        summerHouse1.setFloorCount(2);
        summerHouse1.setPrice(BigDecimal.valueOf(30000000));
        summerHouseList.add(summerHouse1);

        SummerHouse summerHouse2 = new SummerHouse();
        summerHouse2.setCommercialStatus(CommercialStatus.RENTABLE);
        summerHouse2.setRecordStatus(RecordStatus.ACTIVE);
        summerHouse2.setName("Koç Yazlığı");
        summerHouse2.setDescription("Koç ailesine ait yazlık");
        summerHouse2.setSquareMeters(1500);
        summerHouse2.setRoomCount(28);
        summerHouse2.setSaloonCount(10);
        summerHouse2.setFloorCount(2);
        summerHouse2.setPrice(BigDecimal.valueOf(38000000));
        summerHouseList.add(summerHouse2);

        SummerHouse summerHouse3 = new SummerHouse();
        summerHouse3.setCommercialStatus(CommercialStatus.PURCHASEABLE);
        summerHouse3.setRecordStatus(RecordStatus.ACTIVE);
        summerHouse3.setName("Ülker Yazlığı");
        summerHouse3.setDescription("Ülker ailesine ait yazlık");
        summerHouse3.setSquareMeters(2500);
        summerHouse3.setRoomCount(40);
        summerHouse3.setSaloonCount(20);
        summerHouse3.setFloorCount(2);
        summerHouse3.setPrice(BigDecimal.valueOf(58000000));
        summerHouseList.add(summerHouse3);

        return summerHouseList;
    }

}
