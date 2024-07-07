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
        return cPackageList;
    }
}
