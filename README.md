Simple app for tracking sport events using Firebase and SQL db.

Architecture: MVVM

LauncherActivity just for Auth.

SportEventsActivity is for auth users and contains 2 Fragments (CreateEventFragment and Events Fragment).

SaveEventIntentService is a service for saving created event (to Local DB or Firebase). Result of this operation is sent via local broadcast.

TODO:
Tests
Long text (event title or place) in cardView (fragment_events_list_item)
Caching Firebase data
Firebase storage for event images