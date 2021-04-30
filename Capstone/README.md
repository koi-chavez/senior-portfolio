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

## Important Files 
The code for the web application itself lives in /src and /dist. You can view /dist/index.html to view the styles used for the web application and /src/index.js to view the React component being rendered to the webpage. 

To view the code used to configure the Raspberry Pi Web Camera set up you can view:
* motion
* motion.conf

To view the pagekite script used to host the web application outside of the home network you can view:
* pagekite.py