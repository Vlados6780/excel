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

function processImage(input) {
    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = function(e) {
        const img = new Image();
        img.onload = function() {
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');

            const width = img.width;
            const height = img.height;

            // Создаем canvas с размером изображения
            canvas.width = width;
            canvas.height = height;

            // Определяем ориентацию изображения на основе EXIF-данных
            EXIF.getData(img, function() {
                const orientation = EXIF.getTag(this, 'Orientation');

                // Поворачиваем изображение в зависимости от ориентации
                if (orientation === 6) {
                    canvas.width = height;
                    canvas.height = width;
                    ctx.rotate(90 * Math.PI / 180);
                    ctx.drawImage(img, 0, -height);
                } else if (orientation === 8) {
                    canvas.width = height;
                    canvas.height = width;
                    ctx.rotate(-90 * Math.PI / 180);
                    ctx.drawImage(img, -width, 0);
                } else if (orientation === 3) {
                    ctx.rotate(180 * Math.PI / 180);
                    ctx.drawImage(img, -width, -height);
                } else {
                    ctx.drawImage(img, 0, 0);
                }

                // Конвертируем canvas обратно в файл
                canvas.toBlob(function(blob) {
                    const fileInput = new File([blob], file.name, {type: file.type});
                    const dataTransfer = new DataTransfer();
                    dataTransfer.items.add(fileInput);
                    input.files = dataTransfer.files;
                });
            });
        };
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

