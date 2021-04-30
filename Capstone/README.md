# Webcam Application 
This is a web application that displays the web stream coming from a USB web camera connected to a Raspberry Pi. 

## Local Development
Start the local development server:
```javascript
npm run develop 
```

## Access Web Application Outside of Home Network
To access the web application outside of the home network you can run the Pagekite script to host the activity from the localhost onto the pagekite url.
```javascript
pagekite.py 8090 koiseniorproject.pagekite.me 
```