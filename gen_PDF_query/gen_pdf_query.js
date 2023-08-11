const query = document.querySelector('#queryBox');
const queryButton = document.querySelector('.queryButton');
const tableSection = document.querySelector('.resultContainer');
const buttonContainer = document.querySelector('.queryButtonContainer');
const main = document.querySelector('.mainContainer')
const textC = document.querySelector('.textContainer')
const spinner = document.querySelector('.spinner')
const resultArr = [];

query.addEventListener('keypress',e=>{
  if(query.value.length>0){
    if(e.key==='Enter'){
      e.preventDefault();
      queryButton.click();
    }
  }
})


queryButton.addEventListener('click', async ()=>
{   
    tableSection.innerHTML = "";
   let code = query.value;
   if(code.length > 0){ 
    code = code.replace(/ {2,}/g," ")
    const codeobj = new Object();
   codeobj['query'] = code; 
   console.log(codeobj);
   const codeJson = JSON.stringify(codeobj)
    console.log(code)
    spinner.classList.remove('hidden');
    try{
        const queryResponse = await fetch('http://localhost:8080/PDFGenerator/api/query/get',{
            headers: {
               'Content-Type' : 'application/json'
            },
            method: 'POST',
            body: codeJson,
          });
          if(queryResponse.ok){
            const data = await queryResponse.json();
            console.log("data",data)
            const {values,columnnames:column} = data;
                if(values){
                  // const buttonContainerChildren = document.querySelectorAll(".queryButtonContainer");
                    const sendObj = {};
                    // if(buttonContainerChildren.length <=2 ){
                        const download = document.createElement('button');
                        download.innerText = 'Export as PDF'
                        download.classList.add('downloadButton');
                        buttonContainer.appendChild(download)
                      // }
                        download.addEventListener('click',()=>{
                            const fileName = prompt()
                            const dataJSON = JSON.stringify(data, function (key, value) {
                                if (typeof value === 'string') {
                                    // const re= new RegExp(/"/g)
                                    console.log("inside func()");
                                  return value.replace(/"/g, '\\"'); // Escape quotation marks with backslashes
                                }return value;
                              });
                            //  console.log("data : "+data);
                            //  console.log("data log : "+dataJSON);
                            sendObj['result'] = dataJSON;
                            console.log('object of filename and result',sendObj)
                            
                            if(fileName){
                                sendObj['file-name'] = fileName;
                                const sendJson = JSON.stringify(sendObj, function (key, value) {
                                    if (typeof value === 'string') {
                                        console.log("inside func()");
                                      return value.replace(/"/g, '\"'); // Escape quotation marks with backslashes
                                    }return value;
                                  });
                                // sendJson = JSON.stringify(sendObj);
                                console.log('json of sent data',sendJson)
                                fetch('http://localhost:8080/PDFGenerator/api/reports/export',{
                                method: 'POST',
                                headers:{'Content-Type':'application/json'},
                                body: sendJson,
                            
                            })
                            .then(response => response.blob()) // Convert the response to a Blob
                            .then(blob => {
                              createDownloadableLink(blob,sendObj['file-name']);
                            }).catch((err)=>console.error(err))
                            }
                        })
						const table = document.createElement('table');
            tableSection.appendChild(table);
            const ctr =  document.createElement('tr');
            column.forEach(item=>{
                const cth = document.createElement('th');
                cth.innerText = item;
                ctr.appendChild(cth);
            })
            table.appendChild(ctr);
            values.forEach(item=>{
                const vtr = document.createElement('tr');
                item.forEach(dataitem=>{
                    const vtd = document.createElement('td');
                    vtd.innerText = dataitem;
                    vtr.appendChild(vtd);
                })
                table.appendChild(vtr);
            })
                    
                }
            const {actual_error, error} = data;
            if(actual_error){
                    //   const errorContainer = document.createElement('div');
                    // const err = document.createElement('div');
                    // err.classList.add('errorDiv');
                    // err.innerText = error;
                    setError(actual_error+"\n"+error);
                    // errorContainer.classList.add('errorContainer');
                    // errorContainer.innerText = actual_error;
                    // errorContainer.appendChild(err);
                    // textC.appendChild(errorContainer)    
            }
            resultArr.push(data)
            // const column = data.columnnames.substring(1, data.columnnames.length - 1).split(', ');
            
          }
          else{
            setError('Request failed')
          }
    }
    catch(err){
        setError(err)
    }
    spinner.classList.add('hidden');
   }
   else{
    setError('No query found, textbox is empty');
   }  
   
   
   function createDownloadableLink(blob,fileName) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${fileName}.pdf`; // Set the desired file name
    document.body.appendChild(a);
    a.click(); // Programmatically click the link to trigger the download
    document.body.removeChild(a); // Clean up the temporary link element
    URL.revokeObjectURL(url); // Release the object URL
  //  buttonContainer.removeChild(download);
  }
})

function setError(msg){
  const errorContainer = document.createElement('div');
  errorContainer.classList.add('errorContainer')
  const err = document.createElement('div');
  err.classList.add('errorDiv');
  err.innerText = msg;
  errorContainer.appendChild(err);
  textC.appendChild(errorContainer);
  setTimeout(()=>textC.removeChild(errorContainer),3500);
}