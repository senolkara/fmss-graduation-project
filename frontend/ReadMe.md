# FMSS FullStack Bootcamp Bitirme Projesi 

> app dizini içerisinde layout.tsx, loading.tsx, Home page.tsx, sistemin genelinde kullandığım page.tsx yapılarım ve api routelarım mevcuttur.

> api dizini içerisinde backend tarafındaki endpointlere erişebildiğim yapılarım mevcuttur. route.ts şeklinde yapılandırılmıştır.
>

Login ve Register işlemleri için api routelar
> login için api route
> /api/auth/login
> 
> register için api route
> /api/auth/register
>

İlanlar için api routelar
> ilan oluşturabildiğimiz api route
> /api/advertisement/create 
>
ilan güncellebildiğimiz api route
> /api/advertisement/update
>
> ilanları listeleyebildiğimiz api route
> /api/advertisement/get-all-by-customer-id
>
> ilan detayı için api route
> /api/advertisement/get-by-id
>

Yapıları(Binalar) için api routelar
> YapılarıBinalar) çektiğim api route
> /api/building/get-all
>

Satınalınabilir paketler için api routelar
> Satınalınabilir paketleri çektiğim api route
> /api/cpackage/get-all
>
> Paket detayı için api route
> /api/cpackage/get-by-id
>

Müşteriler için api routelar
> Müşteri detayı api route
> /api/customer/get-by-user-id
>

Müşteri paketleri için api routelar
> Müşteriye tanımlanan paketleri çektiğim api route
> /api/customer-package/get-all-by-customer-id
> 

Siparişler için api routelar
> Sipariş oluşturulan api route
> /api/order/create
> 
> Verilen siparişleri çektiğim api route
> /api/order/get-all-by-customer-id
> 

Login ve Register işlemleri için page.tsx sayfaları
> Login sayfası için page.tsx
> /app/auth/login
> 
> Register sayfası için page.tsx
> /app/auth/register
> 

İlanları düzenleyebildiğimiz page.tsx sayfası
> /app/advertisement/update/[id]
> 


Satınalınabilir paketleri görüntüleyebildiğimiz page.tsx 
> /app/cpackage/view
>

Kullanıcı dashboard page.tsx
> /app/dashboard
> 

Seçilen paket ile sipariş oluşturma sayfası page.tsx
> /app/order/create-order/[cpackageId]
> 

Dashboard için verileri return ettiğim Tiles.tsx
> /app/tilesDatas
> 

components dizini içerisinde modal, dataTable, Navbar, Sidebar, Footer bulunmaktadır

İlanları listeleyebildiğimiz dataTable
> /components/AdvertisementsDataTable.tsx
> 

İlan oluşturabildiğimiz modal
> /components/AdvertisementCreateModal.tsx
> 

Satınalınabilir paketleri listeleyebildiğimiz dataTable
> /components/CPackagesDataTable.tsx
> 

Müşteri paketlerini listeleyebildiğimiz dataTable
> /components/CustomerPackagesDataTable.tsx
> 

Siparişleri listeleyebildiğimiz dataTable
> /components/OrdersDataTable.tsx
> 

Services dizini içerisinde apilerimize erişebileceğimiz index.ts yapılarım mevcuttur

İlanlar index.ts
> /Services/advertisement
> 

Login ve Register index.ts
> /Services/auth
> 

Yapılar(Binalar) index.ts
> /Services/building
> 

Satınalınabilir paketler index.ts
> /Services/cpackage
> 

Müşteriler index.ts
> /Services/customer
> 

Müşteri paketleri index.ts
> /Services/customer-package
> 

Siparişler index.ts
> /Services/order
> 

Kullanıcı index.ts
> /Services/user
> 

Store dizini içerisinde sliceları depoladığım yapı mevcuttur
> /Store/store.ts
> 

utils dizini içerisinde slicelarım mevcuttur

İlanlar slice
> /utils/AdvertisementDataSlice.ts
> 

Yapı(Bina) slice
> /utils/BuildingDataSlice.ts
> 

Satınalınabilir paketler slice
> /utils/CPackageDataSlice.ts
> 

Müşteri slice
> /utils/CustomerDataSlice.ts
> 

Müşteri paketleri slice
> /utils/CustomerPackageDataSlice.ts
> 

Siparişler slice
> /utils/OrderSlice.ts
> 

Kullanıcılar slice
> /utils/UserDataSlice.ts
> 

Aktif Nav bilgisi slice
> /utils/UserNavSlice.ts
> 


