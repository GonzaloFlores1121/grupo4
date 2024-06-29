const mercadopago = new MercadoPago('TEST-2f2a2c69-f2b2-4d6b-813a-724e79d499db', {
    locale: 'es-AR' // The most common are: 'pt-BR', 'es-AR' and 'en-US'
});

const MP= async ()=>{

    try {
        miArticulo = {}
        miArticulo.title = "membresia";
        miArticulo.quantity = "1";
        miArticulo.unit_price = "1000";

        const response = await fetch("api/mp", {
            method: "POST",
            headers: {
                'Accept': 'Application/json',
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(miArticulo)
        })
        const preference = await response.text()
        createCheckoutButton(preference)
        console.log(preference);
    } catch (e) {
        alert(e)
    }
}


// Handle call to backend and generate preference.
const createCheckoutButton = (preference_OK)=>{
    const bricksBuilder = mp.bricks();
    const generateButton=async ()=>{
        if(window.checkoutButton) window.checkoutButton.unmount()
        await bricksBuilder.create(
            'wallet',
            'button-container', // class/id where the payment button will be displayed
            { initialization: {
            preferenceId:preferenceId_OK,
                },
            });
    }
    generateButton();
}
document.getElementById('checkout-button').addEventListener('click', MP);