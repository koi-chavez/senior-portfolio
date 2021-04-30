import ReactDOM from "react-dom";
import React from "react";

function myFunction() {
  const { exec } = require("child_process");

  exec("ssh pi@10.0.0.19 sudo service motion start", (error, stderr) => {
      if (error) {
          console.log(`error: ${error.message}`);
          return;
      }
      if (stderr) {
          console.log(`stderr: ${stderr}`);
          return;
      }
      console.log(`Success`);
  });

  console.log(`End`);
}

const PageHtml = () => {
  return (
    <div>
      <h1>Koi's Security Webcam App</h1>
      <h3>Ran using Raspberry Pi, Javascript, Node.js and React</h3>
      <iframe
        src="https://hostfeed-koiseniorproject.pagekite.me/"
        frameBorder="0"
        allow="autoplay; clipboard-write; encrypted-media; gyroscope;"
        allowFullScreen
      ></iframe>
      <hr />
      <ul>
        <li>
        <a href="https://hostfeed-koiseniorproject.pagekite.me/" target="blank"><button>View</button></a>
        </li>
      </ul>
      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdnrPI0HO7VbgyvZ1iOJbJalX169DzDvsXxw&usqp=CAU" />
    </div>
  );
};

ReactDOM.render(<PageHtml />, document.getElementById("React-App"));
