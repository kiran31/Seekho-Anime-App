I built a modern, offline-first anime browser app using the Jikan API.

What I Built
I wanted to go beyond the basic requirements and build an app that was both robust and enjoyable to use. Here are some of the key features I implemented:

Works Completely Offline: Once you've browsed some anime, the app caches everything. You can close it, turn off your Wi-Fi, and still see all the data you've loaded. I added a small banner to let you know when you're offline.

Modern, Tabbed UI: The app is built 100% with Jetpack Compose and Material 3. I added a bottom navigation bar to separate the main "Explore" feed from your "Favorites" and an "About" page.

Infinite Scrolling: The main feed uses the Paging 3 library to load anime as you scroll, so it's always fast and responsive.

Save Your Favorites: You can tap the heart on any anime to save it to your local favorites list, which is also available offline.

Smooth Experience: Instead of a jarring loading spinner, I implemented a shimmer effect for the initial load. I also made sure the search bar was efficient by debouncing user input.

Fully Tested: I wrote both local JUnit tests for the networking/repository logic and instrumented tests to make sure the database and UI navigation work as expected.

Tech Stack
I chose a modern, industry-standard tech stack for this project:

UI: Jetpack Compose

Architecture: MVVM

Dependency Injection: Dagger-Hilt

Concurrency: Kotlin Coroutines & Flow

Networking: Retrofit

Database: Room (for offline caching)

Paging: Paging 3 with a RemoteMediator

Testing: JUnit, Robolectric, Hilt & Compose Test Rules

How to Run It
Clone the repository.

Open the project in a recent version of Android Studio (Hedgehog or newer).

Let Gradle sync, then build and run on an emulator or device.

A Note on My Approach
A couple of things I considered while building this:

Trailers: Instead of embedding a YouTube player, which can be heavy, I chose to have the trailer button open an external app (like the browser or YouTube app). It's a simpler and more lightweight solution.

Handling Missing Data: The UI is built with flexible components. If the API ever stopped sending images, for example, the layout wouldn't crash or look broken. It would adapt gracefully.

Thanks for the opportunity! I had a lot of fun building this.
