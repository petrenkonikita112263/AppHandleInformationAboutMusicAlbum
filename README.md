# REST Api for Last.fm

Here's my second project made according to standards of perfomance 2nd Lab's project from Netcracker's course "Java programming language".

This RESTful application is based on Spring Boot and was created with Intellij IDEA on Java language. This helpful-app is receiving the data about music album in JSON or XML format according to introduced the title of the album and its artist.

# For testing
Run this app, open ani API handle program like Postman, I recommend Insomnia.
Create new GET request and type this or just copy:
http://localhost:9910/async/album/Robbie%20Williams/The%20Heavy%20Entertainment%20Show.xml

That example for using async method, simple request is the same but without *async*
http://localhost:9910/album/

P.S. Don't forget the user can anytime changed the title of the album or even signer, and the response type not XML, but JSON or delete the extension in the end (by default it'll be JSON file).

Finally, if user wants to download (save) his|her favorite album, he or she shall use this request in any *web-browser*:
http://localhost:9910/download/album/Robbie%20Williams/The%20Heavy%20Entertainment%20Show

# Important
The user should have installed *Lombok* plugin for app's work.