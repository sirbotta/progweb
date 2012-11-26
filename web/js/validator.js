/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$('#add-product-id').validate({		
rules: {
        product: 'required',
        qnt: 'required'
},

messages: {
        product: "<span class='err'>cazzo vendi?</span>",
        qnt: "<span class='err'>cazzo vendi?</span>"
}

});