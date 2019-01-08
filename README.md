# ToDoListApp

This version is slightly different than the app store version. Here the app is split into two activites, the main one, and a second one for adding a new task. I did this to try out the MVP architecture of splitting up the View and Presenter, and how a new activity request would be implemented. I left the app store version simpler because it is easier to use that way, and I actually ended up using it regularly as my simple list app.


The second activity does not have a MVP structure, since it seemed simpler just to pass back the String directly. However, let's say it did, and the "Model" was not just a simple file I/O operation, but instead a remote database connection. Then, would it make more sense to create separate connection requests in each activity instead of trying to somehow pass a live connection using Parcelable?
