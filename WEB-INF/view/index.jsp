<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
    <title>UrgVerif - Accueil</title>
</head>
<body>
    
    <header>
        <h1>UrgVerif</h1>
    </header>

    <main>
        <section id="presentation">
            <h2>UrgVerif, c'est quoi ?</h2>
            <p>
                UrgVerif est une application web qui vous permet de vous facilitez la vérification de vos engins (VSAV, FPT, FSR, ...).
                Pour accéder à la vérification d'un enfin, il vous suffit de rentrer le numéro d'immatriculation de l'engin et de cliquer sur "Vérifier".
                Vous aurez alors accès à la liste des vérifications à effectuer sur l'engin.
            </p>
        </section>

        <section>
            <h2>Vérifier un engin</h2>
            <form action="startVerif" method="post">
                <label for="immat">Numéro d'immatriculation</label>
                <input type="text" name="immat" id="immat" required>
                <input type="submit" value="Vérifier">
            </form>
        </section>

        <section>
            <h2>Ajouter un engin ?</h2>
            <p>
                Vous souhaitez ajouter un engin à la base de données ? Cliquez <a href="addEngin">ici</a>.
            </p>
        </section>

    </main>

    <footer>
        <p>&copy; 2024 UrgVerif - Tous droits réservés</p>
        <p>Made by <a href="https://valentin-thuillier.fr/">Valentin THUILLIER</a></p>
    </footer>

    <script>
        // This script check if the immatriculation is valid
        const immatInput = document.querySelector('form #immat');
        const submitBtn = document.querySelector('form input[type="submit"]');
        immatInput.addEventListener('input', () => {
            const value = immatInput.value.replaceAll(' ', '').replaceAll('-', '').toUpperCase();
            // Exemple: fr-941-yq -> FR941YQ
            console.log(value);
            if (immatInput.value.length > 0) {
                if (!/^[A-Z]{2}[0-9]{3}[A-Z]{2}$/.test(value)) {
                    submitBtn.setAttribute('disabled', 'disabled');
                } else {
                    console.log('ok');
                    submitBtn.removeAttribute('disabled');
                }
            }
        });
        
        submitBtn.addEventListener('click', () => {
            immatInput.value = immatInput.value.replaceAll(' ', '').replaceAll('-', '').toUpperCase();
            if (!/^[A-Z]{2}[0-9]{3}[A-Z]{2}$/.test(immatInput.value)) {
                alert('Le numéro d\'immatriculation n\'est pas valide');
                return false;
            } 
        });

    </script>

</body>
</html>