package com.senolkarakurt.cpackageservice.util;

import com.senolkarakurt.cpackageservice.model.CPackage;
import com.senolkarakurt.enums.PackageType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateFakeData {

    public static List<CPackage> getCPackageList(){
        List<CPackage> cPackageList = new ArrayList<>();
        CPackage cPackage = new CPackage();
        cPackage.setPackageType(PackageType.PREMIUM);
        cPackage.setDescription("Premium Paket");
        cPackage.setPrice(BigDecimal.valueOf(10000));
        cPackageList.add(cPackage);

        CPackage cPackage2 = new CPackage();
        cPackage2.setPackageType(PackageType.GOLD);
        cPackage2.setDescription("Gold Paket");
        cPackage2.setPrice(BigDecimal.valueOf(15000));
        cPackageList.add(cPackage2);

        CPackage cPackage3 = new CPackage();
        cPackage3.setPackageType(PackageType.PLATINUM);
        cPackage3.setDescription("Platin Paket");
        cPackage3.setPrice(BigDecimal.valueOf(20000));
        cPackageList.add(cPackage3);

        CPackage cPackage4 = new CPackage();
        cPackage4.setPackageType(PackageType.STANDARD);
        cPackage4.setDescription("Standard Paket");
        cPackage4.setPrice(BigDecimal.valueOf(500));

        cPackageList.add(cPackage4);
        return cPackageList;
    }
}
