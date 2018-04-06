# Movies

This is a simple application to demonstrate CLEAN architecture.
Data is provided from [themoviedb](https://developers.themoviedb.org)
Feel free to build & try it!

## Tech stack
 - **Retrofit + OkHttp**
 - **Dagger2**
 - **RxJava2**
 - **Glide**
 - **Android Architecture Components : Extensions**
 - **[Paginate](com.github.markomilos:paginate)**

## Code structure
I have created 4 top level packages:

 1. **data**: contains components to get the data, also holds the implementation of the domain-defined DataSource interface
 2. **device**: contains implementations for the domain-defined interfaces strongly linked to Android environment like ImageLoader and ResourceHelper
 3. **domain**: defines the business objects like actions, operations, entities, use cases
 4. **presentation**: responsible for presenting data and communicating with domain layer.

Ideally the data and domain layers could be pure kotlin modules without any Android-related dependencies.
Presentation and device would still be application and android library respectively because they have dependencies towards the Android framework.

## Screenshots
![Search page](https://preview.ibb.co/im9CcH/search.png)
![Details Page](https://preview.ibb.co/bKoSBc/details.png)
