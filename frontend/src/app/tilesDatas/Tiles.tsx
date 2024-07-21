"use client"
import React  from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../Store/store";





export default function  GettingDatasLength() {

  const advertisementData = useSelector((state: RootState) => state.User.advertisement);
  const orderData = useSelector((state: RootState) => state.User.order);
  const customerPackageData = useSelector((state: RootState) => state.User.customerPackage);

  return [
    
    {
      icon: "GiAbstract010",
      color: "text-blue-600",
      title: "İlanlarım",
      count: advertisementData?.length
    },
    {
      icon: "GrCompliance",
      color: "text-orange-600",
      title: "Siparişlerim",
      count: orderData?.length
    },
    {
      icon: "TfiStatsUp",
      color: "text-orange-600",
      title: "Paketlerim",
      count: customerPackageData?.length
    },
  ]
}