
    let select = document.getElementById("curName");
    let isResizeble = false;
  function valid()
        {
            let curName = document.getElementById('curName').value;
            let startDate = document.getElementById('startDate').value;
            let endDate = document.getElementById('endDate').value;

            if (curName != "" && startDate && endDate && startDate <= endDate) {
                document.getElementById('action-btn').disabled = false;
                }
            else if(curName != "" && startDate && endDate && startDate > endDate){
                alert('Неверно указан период!')
                document.getElementById('action-btn').disabled = true;
                              }

            else {
                document.getElementById('action-btn').disabled = true;
                }


        }

  async function getValNames(){
      if(!isResizeble) {
        let select = document.getElementById("curName");
        let response = await fetch('http://localhost:8080/currencies')
        let valNames = await response.json();
        for (let i = 0; i < valNames.length; i++){
            let content = valNames[i];
            let option = document.createElement("option");
            option.textContent = content;
            option.value = content;
            select.appendChild(option);
        }

      }
        isResizeble = true;
    }

   const formPost = document.getElementById("form-id");
   let text = {};
   let dailyCurr = {};
   let dateArray = [];
   let currArray = [];
   document.onload = formPost.addEventListener('submit', event => {
    event.preventDefault();
    const formData = new FormData(formPost);
    const data = Object.fromEntries(formData);
    let curr = fetch('http://localhost:8080/dynamics', {
    method: 'POST',
    headers: {
    'Content-type': 'application/json'
    },
    body: JSON.stringify(data)
    })

   curr
    .then(res => {return res.json()})
    .then(respBody => {text = respBody})
    .then(cur =>{
        dailyCur = JSON.stringify(text);
        dailyCur = JSON.parse(dailyCur);
        for (let i = 0; i < dailyCur.length; i++) {
                currArray[i] = dailyCur[i].dailyCurr;
                dateArray[i] = dailyCur[i].date;
        }
        let ctx = document.getElementById('myChart').getContext('2d');
        let chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: dateArray,
                datasets: [{
                    label: 'График изменения курса белорусского рубля за указанный период',
                    borderColor: 'rgb(255,0,51)',
                    data: currArray
                }]
            },
            options: {}
        });
   })

   });
