# FMSS FullStack Bootcamp Bitirme Projesi
### Api Endpointleri ve Postman Sorguları

Kullanıcının register olduğu endpoint ve postman sorgusu:
```sh
http://localhost:8091/api/v1/customers
{
   "name": "senol",
    "surname": "karakurt",
    "email": "snlkrkrt@hotmail.com",
    "password": "987654321"
}
```

Kullanıcının kayıt olduktan sonra login olduğu endpoint ve postman sorgusu
```sh
http://localhost:8090/api/v1/auth/authenticate
{
    "email": "snlkrkrt@hotmail.com"
    ,"password": "987654321"
}
```

Login olduktan sonra siparişin verildiği endpoint ve postman sorgusu
```sh
http://localhost:8097/api/v1/orders
{
    "customerRequestDto":{
        "id": 202
    }
    ,"packageRequestDto": {
        "id": 52
    }
}
```

Müşteri siparişlerinin listelendiği endpoint
```sh
http://localhost:8097/api/v1/orders/customerId/202
```

Verilen siparişin ödemesinin yapıldığı endpoint
> Ödeme senkron, müşteriye paket tanımlama asenkron şeklinde yapılıyor
> 
> Fatura oluşturuluyor
```sh
http://localhost:8098/api/v1/purchases/save
```

Ödeme yapıldıktan sonra asenkron olarak müşteriye tanımlanan paketin listelendiği endpoint
```sh
http://localhost:8095/api/v1/packages/customerId/202
```

Müşsterinin tanımlanan paket üzerinden ilan verebildiği endpoint ve postman sorgusu
> Building classı, sistem ayağa kaldırılırken önceden tanımlanmış olan yapılar(binalar)
> 
> İlan tipleri enum olarak tutuluyor
```sh
TRANSFERABLE: Devren Satılık
PURCHASEABLE: Satılık
RENTABLE: Kiralık
```
> İlan durumları enum olarak tutuluyor
```sh
DRAFT: Taslak
ACTIVE: Aktif
PASSIVE: Pasif
IN_REVIEW
```
> İlan ilk olarak IN_REVIEW durumda kaydediliyor fakat asenkron bir yapıyla durumu ACTIVE olarak update ediliyor
```sh
http://localhost:8096/api/v1/advertisements
{
    "advertisementType": "TRANSFERABLE",
    "price": 179000,
    "buildingRequestDto": {
        "id": 1
    },
    "customerPackageRequestDto": {
        "id": 52
    }
}
```

Müşteri ilanlarının listelendiği endpoint
```sh
http://localhost:8096/api/v1/advertisements/customerId/202
```

Müşteri ilanlarını güncelleyebildiği endpoint ve postman sorgusu
(Burada sadece 3 alanı güncellesin dedim fakat genişletilebilir)
```sh
http://localhost:8096/api/v1/advertisements/update/202
{
    "advertisementStatus": "DRAFT",
    "advertisementType": "PURCHASEABLE",
    "price": 28000
}
```

Bunların dışında:
> Satınalınan pakette varsayılan olarak 10 adet ilan yayınlama hakkı ve ilan bitiş tarihi, satınalınan gün üzerine 30 gün eklenerek tanımlanıyor
> Yeni bir paket aldığında var olan ilan yayınlama hakkının üzerine 10 adet daha hak ilave ediliyor ve ilan bitiş tarihi, tanımlanan bitiş tarihinin üzerine 30 gün daha ekleniyor