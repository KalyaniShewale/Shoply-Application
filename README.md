# Shoply

Shoply is an Android e-commerce app built with **Kotlin** and **Jetpack Compose**. Users can register and log in, browse products, view product details, save favorites on their device, and (work in progress) upload their own products.

It was built as an assignment to practice modern Android development: MVVM, clean architecture, dependency injection with Hilt, Firebase, Room and Retrofit.

---

## Screenshots

![App Home Screen](screenshots/favorites.png)

Add your screenshots to a `screenshots/` folder in the repository and update the file names below.

| Login | Register | Home |
|---|---|---|
| ![Login](screenshots/login.png) | ![Register](screenshots/register.png) | ![Home](screenshots/home.png) |

| Product details | Favorites | Upload product |
|---|---|---|
| ![Details](screenshots/details.png) | ![Favorites](screenshots/favorites.png) | ![Upload](screenshots/upload.png) |

---

## Features

### Authentication
- Register with name, email, phone number and password (Firebase Authentication)
- Log in with email and password
- Clear error messages for wrong credentials, invalid email, existing email, weak password, no internet and too many attempts
- The session is remembered: the app opens on Home if the user is already logged in
- Log out from the Home screen

### Home
- List of products loaded from the [FakeStore API](https://fakestoreapi.com/) using Retrofit and Gson
- Each item shows the product image, title, short description and price
- Search by title or description
- Filter by category
- Loading, empty and error states, with a "Try again" button
- Tap the heart to add or remove a favorite

### Product details
- Image gallery with swipe and page dots
- Title, category, price and full description
- Seller name and contact details with a call button (for products uploaded by users)
- Add or remove the product from favorites

### Favorites
- Favorites are stored locally in a **Room** database
- A full copy of each favorite is saved, so the list works offline and survives closing the app
- Remove a favorite with the heart, with an empty state when nothing is saved

### Upload product (in progress)
- Form with photos (minimum 3), title, category, description and price, with validation
- Saving to Firebase Storage and Firestore is the next step (work in progress)

### Navigation
- Navigation Compose with type-safe routes
- Bottom navigation bar with Home, Favorites and Upload tabs

---


## Architecture

The app follows MVVM with a clean separation of layers. The UI never talks to Firebase, Room or Retrofit directly.

```
Screen (Compose)  ->  ViewModel  ->  Use case  ->  Repository (interface)  ->  Data source
       ^                  |                                                   Firebase / Room / Retrofit
       +---- StateFlow ----+
```

- **UI layer**: Compose screens, reusable components and ViewModels. A ViewModel exposes a `StateFlow` of UI state. One-time actions, such as navigating after login, use a `Channel`.
- **Domain layer**: plain Kotlin models (`Product`, `Seller`, `User`), repository interfaces and use cases. It has no Android or Firebase code.
- **Data layer**: repository implementations, the Retrofit API, DTOs for each data source, the Room database and the Firebase calls.
- **DI layer**: Hilt modules that tell Hilt how to create Firebase, Retrofit, Room and the repositories.

Each data source has its own small class that matches its format (`FakeStoreProductDto`, `ProductDto`, `FavoriteEntity`) and is converted to the shared `Product` model. The screens only ever see `Product`.

---

## Author

Kalyani Shewale


