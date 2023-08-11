const edit = document.querySelectorAll('.editButton');
const allInput = document.querySelectorAll('input');
const titleinp = document.querySelector('#titleInput');
const addressinp = document.querySelector('#addressInput');
const phoneinp = document.querySelector('#phoneInput');
const email = document.querySelector('#emailInput');
const img = document.querySelector('.logoImage')
const changeImg = document.querySelector('.changeImage');
const fileButton = document.querySelector('.chooseFileButton')
const loader = document.querySelector('.loaderContainer'); 
const responseSection = document.querySelector('.responseMessage')
const closeResponse = document.querySelector('.closeButton');
const spinner = document.querySelector('.spinner');

closeResponse.addEventListener('click',()=>{
  responseSection.classList.add('invisible');
})

fileButton.addEventListener('click',(e)=>{
  if(changeImg.hasAttribute('disabled')){
    changeImg.removeAttribute('disabled');
  }
  e.preventDefault()
  changeImg.click()
})
// allInput.forEach((item)=>{item.addEventListener('click',()=>item.setAttribute("disabled", 'true'))})

changeImg.addEventListener('change',(e)=>{
   const reader = new FileReader();
   reader.readAsDataURL(e.target.files[0]);
   reader.onload = function(){
      const imgUrl = reader.result;
      img.setAttribute('src',imgUrl);
   }
})

edit.forEach((item)=>{item.addEventListener('click', (e)=>{
   e.preventDefault();
   const parent = e.target.parentElement;
   const child = parent.querySelector(`#${e.target.id}Input`)
   if(child.hasAttribute('disabled') ){
   child.removeAttribute('disabled')}
   else{
    child.setAttribute('disabled',true)
   }
})})


async function fetchData(){
const response = await fetch("http://localhost:8080/PDFGenerator/api/headerdata/view")
.then(response=>{
    if(!response.ok){
        console.log('failed')
    }
    else{
        return response.json();
    }
})
.then(data =>{
    const obj = data;
    const {title,address,contactNo, mailID, logo} = obj;
    titleinp.value = title
    addressinp.value = address;
    phoneinp.value = contactNo;
    email.value = mailID;
    img.src = `data:image/jpg;base64,${logo}`;
})
.catch(error=>{console.log(error)})
}
fetchData();

const imgTo64 = url =>fetch(url)
  .then(img => img.blob())
  .then(imgblob => new Promise((resolve,reject)=>{
      const fr = new FileReader();
      fr.onloadend = ()=> resolve(fr.result)
      fr.onerror = reject
      fr.readAsDataURL(imgblob);
    }))

//Detect the change in files...
changeImg.addEventListener('change',()=>{
  changeImg.classList.add('changed');
  const fr = new FileReader();  
  fr.onloadend = ()=>{
    img.src = fr.result;
  }
  fr.readAsDataURL(changeImg.files[0]);
})

//Fetch every data in the form using FormData as soon as the submit event happens
const formSection = document.querySelector('.form');
formSection.addEventListener('submit',async (e)=>{
  e.preventDefault();
  spinner.classList.remove('invisible')
//Enabling all the inputs to submit the form
  allInput.forEach(input=>{
    if(input.hasAttribute('disabled')){
      input.removeAttribute('disabled')
    }
  })
  const formInput = new FormData(formSection);
  if(!changeImg.classList.contains('changed')){
    await imgTo64(img.src).then(data=>{formInput.append('logo', data.split(',')[1])})
    
  }
  else{
    formInput.append('logo',img.src.split(',')[1])
  }
  const formObj = Object.fromEntries(formInput);
  const formJson = JSON.stringify(formObj);
  await fetch("http://localhost:8080/PDFGenerator/api/headerdata/update",{
    headers: {'Content-Type': 'application/json'},
    body: formJson,
    method: 'POST',
  })
  .then(response=>response.json()).then(dta=>{
    const {data} = dta;
    cons
    console.log(dta);
    spinner.classList.add('invisible');
    responseSection.classList.remove('invisible');
    allInput.forEach(input=>{
      if(!input.hasAttribute('disabled')){
        input.setAttribute('disabled',true);
        fetchData();
      }
    })
  })
    .catch((err)=>console.error(err))
    spinner.classList.add('invisible')
})

