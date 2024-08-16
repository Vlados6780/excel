function showProviderInputs(fileInput) {
    const providerGroup = fileInput.parentNode;
    const textInputs = providerGroup.querySelectorAll('input[type="text"]');
    textInputs.forEach(input => input.classList.add('visible'));
}

function showProductInputs(fileInput) {
    const productGroup = fileInput.parentNode.nextElementSibling;
    const textInputs = productGroup.querySelectorAll('input[type="text"]');
    textInputs.forEach(input => input.classList.add('visible'));
}

function addProductBlock() {
    const form = document.getElementById('productForm');
    const productBlockCount = document.querySelectorAll('.product-block').length;
    const block = document.createElement('div');
    block.className = 'product-block';
    block.innerHTML =
        `<div class="input-group provider-group">
            <input type="file" name="products[${productBlockCount}].imageOfProvider" accept="image/*" capture="environment" required onchange="showProviderInputs(this)" />
            <input type="text" name="products[${productBlockCount}].nameOfProvider" placeholder="Enter the name of provider" />
        </div>
        <div class="input-group product-group">
            <input type="file" name="products[${productBlockCount}].imageOfProduct" accept="image/*" capture="environment" required onchange="showProductInputs(this)" />
        </div>
        <div class="input-group product-group">
            <input type="text" name="products[${productBlockCount}].descriptionOfProduct" placeholder="Enter Comments" />
            <input type="text" name="products[${productBlockCount}].price" placeholder="Enter the Price" />
            <input type="text" name="products[${productBlockCount}].moq" placeholder="Enter the MOQ" />
            <input type="text" name="products[${productBlockCount}].ctn" placeholder="Enter the CTN" />
        </div>`;
    form.insertBefore(block, document.getElementById('addBlockBtn'));
}

