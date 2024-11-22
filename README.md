
# Airbnb Angular - Spring

Proyecto para curso de capacitación de programa Learning to doing de Airbnb basado en Angular para front-end y Spring Framework para back-end

## Run Locally

### 1. Instalar JDK y Maven
Sigue los pasos detallados en el siguiente PDF para instalar **JDK** y **Maven** en tu máquina:  
[Cómo instalar Java y Maven](https://github.com/mReichert-i2t/CursoJava/blob/main/C%C3%B3mo%20instalar%20Java_.pdf)

### 2. Clonar el proyecto
Clona el repositorio en el directorio de tu preferencia:

```bash
git clone https://github.com/GonzaBianchi/Airbnb.git
```
### 3. Abrir el proyecto con Visual Studio Code
Dirígete al directorio clonado:

```bash
cd Airbnb
```
Luego, abre el proyecto con Visual Studio Code:
```bash
code .
```
### 4. Instalar dependencias del frontend
En la misma terminal, dirígete al directorio del frontend:

```bash
cd airbnb-front
```
Instala las dependencias:
```bash
npm i
```
### 5. Configurar la base de datos
- Crea una base de datos llamada `airbnb` en el gestor de base de datos que prefieras (como **DBeaver**, **phpMyAdmin**, o **XAMPP**).

‎ 
- Importa el archivo `airbnb.sql` que se encuentra dentro del directorio clonado para cargar las tablas y los datos necesarios.

### 6. Renombrar el archivo `JwtAuthenticationFilter.java`
En Visual Studio Code:
- Ve al archivo `JwtAuthenticationFilter.java` en la ruta:`Airbnb\biblioteca-santa-fe\src\main\java\com\gob\biblioteca_santa_fe\utils`
- Copia el nombre de la clase dentro de ese archivo y renombra el archivo con el mismo nombre. 

### 7. Iniciar el backend
- Ve al archivo `BibliotecaSantaFeApplication.java` en la ruta: `Airbnb\biblioteca-santa-fe\src\main\java\com\gob\biblioteca_santa_fe`

- Haz clic en el botón Run (que aparece arriba del nombre de la clase en Visual Studio Code).
- Espera hasta que la terminal muestre el siguiente mensaje: `INFO 10496 --- [biblioteca-santa-fe] [           main] c.g.b.BibliotecaSantaFeApplication       : Started BibliotecaSantaFeApplication in X.XXX seconds (process running for X.XXX)`

### 8. Iniciar el frontend
Abre una nueva terminal.

Dirígete nuevamente a la carpeta del frontend:
```bash
cd airbnb-front
```
Inicia el servidor de Angular:
```bash
npx ng serve
 ```

### 9. Acceso al proyecto
Una vez que ambos servidores (backend y frontend) estén en ejecución, accede al proyecto desde tu navegador en:
`http://localhost:4200`

## Autores

- [@Gonzalo Bianchini](https://github.com/GonzaBianchi)
- [@Osvaldo Cozzi](https://github.com/osvaldocozzi)
- [@Nicolas Castillo](https://github.com/NicoO1997)


## Contacto

Ante cualquier duda consultar al mail de gonzalobianchini01@gmail.com o osvaldocozzicalvo@gmail.com

