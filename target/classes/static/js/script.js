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
    const container = document.getElementById('productBlocksContainer');
    if (!container) {
        console.error('Container element not found.');
        return;
    }
        const productBlockCount = document.querySelectorAll('.product-block').length;

        const block = document.createElement('div');
        block.className = 'product-block';
        block.innerHTML = `
            <div class="input-group product-group">
                <input type="file" name="products[${productBlockCount}].imageOfProduct" accept="image/*" capture="environment" required onchange="showProductInputs(this)" />
            </div>
            <div class="input-group product-group">
                <input type="text" name="products[${productBlockCount}].descriptionOfProduct" placeholder="Enter Comments" />
                <input type="text" name="products[${productBlockCount}].price" placeholder="Enter the Price" inputmode="decimal" />
                <input type="text" name="products[${productBlockCount}].moq" placeholder="Enter the MOQ"  inputmode="decimal" />
                <input type="text" name="products[${productBlockCount}].ctn" placeholder="Enter the CTN"  inputmode="decimal" />
            </div>`;

        // move down the block of text inputs
        container.appendChild(block);

    // move down the add block button
    const addBlockBtn = document.getElementById('addBlockBtn');
    container.appendChild(addBlockBtn);
}

import { fixImageOrientation } from 'exif-orientation-fixer.js';

async function handleImageOrientation(fileInput, callback) {
    const file = fileInput.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = async (event) => {
        const blob = await fixImageOrientation(event.target.result);
        const url = URL.createObjectURL(blob);
        const img = new Image();
        img.src = url;
        img.onload = () => {
            callback();
        };
    };
    reader.readAsDataURL(file);
}