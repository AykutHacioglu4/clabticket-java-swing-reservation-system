# CLABTicket - Java Swing Reservation System

## English

CLABTicket is a Java Swing desktop application developed for managing bus and airplane seat reservations. The system allows users to register, log in, view available trips, select transportation type, reserve seats, and cancel their own reservations. Admin users can add new trips, view existing trips, and delete trip records.

The project was built to apply advanced object-oriented programming principles in a real desktop application scenario. Instead of keeping the reservation logic directly inside the UI, the system separates user management, trip management, seat layout creation, reservation actions, and UI update logic into different classes and design patterns.

---

## Project Overview

The application has two main user roles:

### 1. Standard User

Standard users can:

* Log in to the system
* Register a new account
* Select transportation type as bus or plane
* View available trips
* Search trips by ID
* Open the seat selection screen
* Reserve available seats
* Cancel only their own reservations
* See updated trip and seat status through the interface

### 2. Admin User

Admin users can:

* Log in with admin privileges
* Add new trips
* Define trip ID, departure, arrival, date, seat count, and vehicle type
* View all existing trips
* Delete selected trips
* Manage the trip list through a separate admin panel

---

## Main Features

* Java Swing-based graphical user interface
* Login and registration system
* User and admin role separation
* Bus and plane trip support
* Dynamic seat layout generation
* Different seat arrangements for bus and plane trips
* Seat reservation and cancellation
* Trip search by ID
* Admin trip creation and deletion
* JTable-based trip listing
* Observer-based UI refresh after data changes
* Unit tests for core reservation, user, factory, and trip management logic

---

## Design Patterns Used

### Factory Pattern

The Factory Pattern is implemented through the `UserFactory` class.

It creates either a standard `User` object or an `Admin` object based on the selected role. This keeps user creation logic in one place and avoids spreading object creation conditions across the application.

Used in:

* User registration
* Default user creation
* Role-based object creation

---

### Strategy Pattern

The Strategy Pattern is used for generating different seat layouts depending on the transportation type.

The `SeatArrangementStrategy` interface defines the common behavior, while `BusSeatLayoutStrategy` and `PlaneSeatLayoutStrategy` provide different layout implementations.

This makes the seat layout system flexible. The application can switch between bus and plane seat arrangements without changing the main seat selection logic.

Used in:

* Bus seat layout generation
* Plane seat layout generation
* Runtime layout selection based on trip type

---

### Command Pattern

The Command Pattern is used for reservation and cancellation operations.

The `Command` interface is implemented by:

* `SeatReserveCommand`
* `CancelReservationCommand`

Each command object represents a specific action. This separates the button/UI interaction from the actual business logic that updates the trip and seat state.

Used in:

* Seat reservation
* Seat cancellation
* Separating UI events from operation logic

---

### Observer Pattern

The Observer Pattern is used to update the interface when trip or seat data changes.

Classes such as `Trip` and `TripManager` extend `Observable`, while UI components such as `UserFrame` and `SeatSelectionDialog` implement `Observer`.

When a seat is reserved, canceled, or when trip data changes, the related interface components are notified and refreshed automatically.

Used in:

* Updating seat status after reservation
* Updating trip lists after changes
* Keeping UI components synchronized with data changes

---

## Main Classes

### `OOPProject`

Entry point of the application. It creates sample users and trips, initializes the `TripManager`, and starts the login screen.

### `User`

Represents a standard system user. Includes login validation and basic user information.

### `Admin`

Extends the `User` class and represents users with admin privileges.

### `UserFactory`

Creates `User` or `Admin` objects based on the selected role.

### `Trip`

Represents a trip with ID, departure, arrival, date, type, seat list, reservation owners, and reservation status.

### `TripManager`

Stores and manages all trips. Provides methods for adding, deleting, finding, and listing trips.

### `LoginFrame`

Provides the login screen and routes users to the correct panel based on their role.

### `RegisterDialog`

Allows new users or admins to register.

### `UserFrame`

Main panel for standard users. Displays available trips, allows searching, type switching, reservation, cancellation, and logout.

### `AdminFrame`

Main panel for admin users. Allows adding new trips and opening the trip list management screen.

### `SeatSelectionDialog`

Displays seats for the selected trip and allows reservation or cancellation actions.

### `SeatButtonFactory`

Creates seat buttons with visual states depending on whether a seat is available or occupied.

### `BusSeatLayoutStrategy`

Creates a bus-style seat layout.

### `PlaneSeatLayoutStrategy`

Creates a plane-style seat layout.

### `SeatReserveCommand`

Handles the seat reservation action.

### `CancelReservationCommand`

Handles the seat cancellation action.

### `Observable` and `Observer`

Provide the update-notification structure used to refresh UI components when data changes.

---

## Technologies Used

* Java
* Java Swing
* NetBeans
* Object-Oriented Programming
* JUnit
* GUI Development
* Design Patterns
* Event-Driven Programming

---

## Testing

The project includes unit tests for core classes and operations.

Tested parts include:

* User login validation
* UserFactory object creation
* Trip creation and seat operations
* Seat reservation command
* Seat cancellation command
* TripManager add, delete, find, and list operations

Testing helped verify that the main business logic works independently from the graphical user interface.

---

## What I Implemented and Learned

In this project, I implemented a complete desktop reservation system with role-based access, dynamic seat selection, trip management, and multiple object-oriented design patterns.

I practiced separating the graphical user interface from the business logic. Reservation and cancellation operations are handled through command classes, seat layouts are generated through strategy classes, users are created through a factory class, and UI updates are handled through observer-based notifications.

This project helped me understand how design patterns make an application easier to extend and maintain. For example, adding another transportation type would not require rewriting the whole seat selection system; a new layout strategy could be added instead. Similarly, new user types could be handled through the factory structure.

Main topics I practiced:

* Java Swing GUI development
* Role-based application flow
* Object-oriented class design
* Factory Pattern
* Strategy Pattern
* Command Pattern
* Observer Pattern
* Event-driven programming
* Dynamic seat layout generation
* Reservation and cancellation logic
* Admin/user panel separation
* Unit testing with JUnit
* Managing application state through separate manager classes

---

# CLABTicket - Java Swing Rezervasyon Sistemi

## Türkçe

CLABTicket, otobüs ve uçak seferleri için koltuk rezervasyonu yönetimi yapan bir Java Swing masaüstü uygulamasıdır. Sistem; kullanıcıların kayıt olmasına, giriş yapmasına, mevcut seferleri görüntülemesine, ulaşım türü seçmesine, koltuk rezerve etmesine ve kendi rezervasyonlarını iptal etmesine olanak sağlar. Admin kullanıcılar ise yeni sefer ekleyebilir, mevcut seferleri görüntüleyebilir ve sefer kayıtlarını silebilir.

Bu proje, gelişmiş nesne yönelimli programlama prensiplerini gerçek bir masaüstü uygulaması senaryosu üzerinde uygulamak için geliştirildi. Rezervasyon mantığını doğrudan arayüz kodunun içine yazmak yerine; kullanıcı yönetimi, sefer yönetimi, koltuk düzeni oluşturma, rezervasyon işlemleri ve arayüz güncelleme mantığı ayrı sınıflara ve design pattern yapılarına bölündü.

---

## Proje Özeti

Uygulamada iki ana kullanıcı rolü bulunmaktadır:

### 1. Standart Kullanıcı

Standart kullanıcılar şunları yapabilir:

* Sisteme giriş yapma
* Yeni hesap oluşturma
* Ulaşım türünü otobüs veya uçak olarak seçme
* Mevcut seferleri görüntüleme
* Sefer ID’sine göre arama yapma
* Koltuk seçim ekranını açma
* Boş koltukları rezerve etme
* Yalnızca kendi rezervasyonlarını iptal etme
* Güncel sefer ve koltuk durumunu arayüz üzerinden görme

### 2. Admin Kullanıcı

Admin kullanıcılar şunları yapabilir:

* Admin yetkisiyle giriş yapma
* Yeni sefer ekleme
* Sefer ID, kalkış, varış, tarih, koltuk sayısı ve araç türü tanımlama
* Tüm seferleri görüntüleme
* Seçilen seferleri silme
* Ayrı bir admin paneli üzerinden sefer listesini yönetme

---

## Temel Özellikler

* Java Swing tabanlı grafik arayüz
* Giriş ve kayıt sistemi
* Kullanıcı ve admin rol ayrımı
* Otobüs ve uçak sefer desteği
* Dinamik koltuk düzeni oluşturma
* Otobüs ve uçak için farklı koltuk yerleşimleri
* Koltuk rezervasyonu ve iptali
* Sefer ID’sine göre arama
* Admin tarafından sefer ekleme ve silme
* JTable tabanlı sefer listeleme
* Veri değişikliklerinden sonra Observer mantığıyla arayüz güncelleme
* Rezervasyon, kullanıcı, factory ve sefer yönetimi işlemleri için unit testler

---

## Kullanılan Design Pattern Yapıları

### Factory Pattern

Factory Pattern, `UserFactory` sınıfı ile uygulanmıştır.

Bu sınıf, seçilen role göre standart `User` veya `Admin` nesnesi oluşturur. Böylece kullanıcı oluşturma mantığı tek bir yerde toplanır ve uygulamanın farklı yerlerine dağılmış koşullu nesne oluşturma kodları azaltılır.

Kullanıldığı yerler:

* Kullanıcı kaydı
* Varsayılan kullanıcı oluşturma
* Role göre nesne üretimi

---

### Strategy Pattern

Strategy Pattern, ulaşım türüne göre farklı koltuk düzenleri oluşturmak için kullanılmıştır.

`SeatArrangementStrategy` interface’i ortak davranışı tanımlar. `BusSeatLayoutStrategy` ve `PlaneSeatLayoutStrategy` sınıfları ise farklı koltuk düzeni implementasyonlarını sağlar.

Bu yapı, koltuk yerleşim sistemini esnek hale getirir. Uygulama, ana koltuk seçim mantığını değiştirmeden otobüs ve uçak düzenleri arasında geçiş yapabilir.

Kullanıldığı yerler:

* Otobüs koltuk düzeni oluşturma
* Uçak koltuk düzeni oluşturma
* Sefer türüne göre runtime’da layout seçimi

---

### Command Pattern

Command Pattern, rezervasyon ve iptal işlemleri için kullanılmıştır.

`Command` interface’i şu sınıflar tarafından implemente edilmiştir:

* `SeatReserveCommand`
* `CancelReservationCommand`

Her command nesnesi belirli bir işlemi temsil eder. Bu yapı, buton/arayüz etkileşimi ile sefer ve koltuk durumunu değiştiren iş mantığını birbirinden ayırır.

Kullanıldığı yerler:

* Koltuk rezervasyonu
* Koltuk iptali
* UI eventleri ile işlem mantığını ayırma

---

### Observer Pattern

Observer Pattern, sefer veya koltuk verisi değiştiğinde arayüzün güncellenmesi için kullanılmıştır.

`Trip` ve `TripManager` gibi sınıflar `Observable` yapısını kullanırken, `UserFrame` ve `SeatSelectionDialog` gibi arayüz bileşenleri `Observer` olarak çalışır.

Bir koltuk rezerve edildiğinde, iptal edildiğinde veya sefer verisi değiştiğinde ilgili arayüz bileşenleri otomatik olarak bilgilendirilir ve güncellenir.

Kullanıldığı yerler:

* Rezervasyon sonrası koltuk durumunun güncellenmesi
* Sefer listelerinin değişiklik sonrası yenilenmesi
* Arayüz bileşenlerinin veriyle senkron kalması

---

## Ana Sınıflar

### `OOPProject`

Uygulamanın başlangıç noktasıdır. Örnek kullanıcıları ve seferleri oluşturur, `TripManager` nesnesini başlatır ve login ekranını açar.

### `User`

Standart sistem kullanıcısını temsil eder. Giriş doğrulama ve temel kullanıcı bilgilerini içerir.

### `Admin`

`User` sınıfından kalıtım alır ve admin yetkisine sahip kullanıcıları temsil eder.

### `UserFactory`

Seçilen role göre `User` veya `Admin` nesnesi oluşturur.

### `Trip`

Sefer ID, kalkış, varış, tarih, sefer türü, koltuk listesi, rezervasyon sahipleri ve rezervasyon durumu bilgilerini tutar.

### `TripManager`

Tüm seferleri saklar ve yönetir. Sefer ekleme, silme, bulma ve listeleme işlemlerini sağlar.

### `LoginFrame`

Giriş ekranını sağlar ve kullanıcının rolüne göre doğru panele yönlendirme yapar.

### `RegisterDialog`

Yeni kullanıcı veya admin kaydı oluşturmak için kullanılır.

### `UserFrame`

Standart kullanıcı panelidir. Sefer listeleme, sefer arama, ulaşım türü değiştirme, rezervasyon, iptal ve çıkış işlemlerini içerir.

### `AdminFrame`

Admin kullanıcı panelidir. Yeni sefer ekleme ve sefer listesi yönetim ekranına erişim sağlar.

### `SeatSelectionDialog`

Seçilen seferin koltuklarını gösterir ve rezervasyon/iptal işlemlerinin yapılmasını sağlar.

### `SeatButtonFactory`

Koltuk durumuna göre görsel olarak uygun koltuk butonlarını oluşturur.

### `BusSeatLayoutStrategy`

Otobüs tipi koltuk düzenini oluşturur.

### `PlaneSeatLayoutStrategy`

Uçak tipi koltuk düzenini oluşturur.

### `SeatReserveCommand`

Koltuk rezervasyon işlemini yönetir.

### `CancelReservationCommand`

Koltuk iptal işlemini yönetir.

### `Observable` ve `Observer`

Veri değişikliklerinden sonra arayüz bileşenlerinin güncellenmesini sağlayan bildirim yapısını oluşturur.

---

## Kullanılan Teknolojiler

* Java
* Java Swing
* NetBeans
* Nesne Yönelimli Programlama
* JUnit
* GUI Development
* Design Patterns
* Event-Driven Programming

---

## Testler

Projede temel sınıflar ve işlemler için unit testler bulunmaktadır.

Test edilen bölümler:

* Kullanıcı giriş doğrulama
* UserFactory nesne oluşturma
* Trip oluşturma ve koltuk işlemleri
* Koltuk rezervasyon command işlemi
* Koltuk iptal command işlemi
* TripManager sefer ekleme, silme, bulma ve listeleme işlemleri

Testler, ana iş mantığının grafik arayüzden bağımsız olarak doğru çalıştığını kontrol etmek için kullanılmıştır.

---

## Bu Projede Ne Uyguladım ve Ne Öğrendim?

Bu projede rol tabanlı erişim, dinamik koltuk seçimi, sefer yönetimi ve birden fazla nesne yönelimli design pattern içeren tam bir masaüstü rezervasyon sistemi geliştirdim.

Grafik arayüz ile iş mantığını birbirinden ayırmayı uyguladım. Rezervasyon ve iptal işlemleri command sınıflarıyla, koltuk düzenleri strategy sınıflarıyla, kullanıcı oluşturma factory sınıfıyla ve arayüz güncellemeleri observer tabanlı bildirimlerle yönetildi.

Bu proje, design pattern yapılarını kullanmanın uygulamayı nasıl daha geliştirilebilir ve sürdürülebilir hale getirdiğini anlamamı sağladı. Örneğin, sisteme yeni bir ulaşım türü eklemek gerektiğinde tüm koltuk seçim sistemini baştan yazmak yerine yeni bir layout strategy eklemek yeterli olabilir. Benzer şekilde yeni kullanıcı tipleri factory yapısı üzerinden yönetilebilir.

Bu projede pratik yaptığım ana konular:

* Java Swing ile masaüstü arayüz geliştirme
* Rol tabanlı uygulama akışı oluşturma
* Nesne yönelimli sınıf tasarımı
* Factory Pattern
* Strategy Pattern
* Command Pattern
* Observer Pattern
* Event-driven programming
* Dinamik koltuk düzeni oluşturma
* Rezervasyon ve iptal mantığı
* Admin/kullanıcı panel ayrımı
* JUnit ile unit testing
* Uygulama durumunu ayrı manager sınıflarıyla yönetme
