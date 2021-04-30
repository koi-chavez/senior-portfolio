function myFunction() {
  const { exec } = require("child_process");

  exec("ssh pi@10.0.0.19 sudo service motion start", (error, stdout, stderr) => {
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
}

myFunction();
// buildTree('/Users/kchavez/Senior-Capstone/Code/App/dist');