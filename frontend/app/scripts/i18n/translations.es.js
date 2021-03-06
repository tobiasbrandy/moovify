'use strict';
define([], function() {

  return {
    INDEX_WELCOME: '¡Bienvenido a Moovify!',
    INDEX_SLOGAN: '¿Terminaste la peli?',
    INDEX_MY_FEED: 'Mi Muro',
    HOTTEST_USERS: 'Usuarios destacados',
    ADMIN_TITLE: 'Administrador',
    FOLLOW_TITLE: 'Seguir',
    BOOKMARKED_TITLE:'Guardado',
    ADMIN_PANEL_BTN: 'Panel de administrador',
    MOOVIFY: 'Moovify',
    USER_LOGIN_TITLE: 'Moovify | Iniciar sesión',
    ADMIN_PANEL_TITLE: 'Moovify | Panel de administrador',
    PROFILE_TITLE: "Moovify | Perfil de {{user}}",
    HOME_TITLE: 'Moovify | Inicio',
    MOVIE_TITLE: 'Moovify | {{movie}}',
    POST_CREATE_TITLE: 'Moovify | Crear un post',
    POST_VIEW_TITLE: "Moovify | {{post}}",
    RESET_PASSWORD_TITLE: 'Moovify | Cambiar contraseña',
    SEARCH_TITLE: 'Moovify | Busqueda',
    SIGNUP_TITLE: 'Moovify | Registrarse',
    UPDATE_PASSWORD_TITLE: 'Moovify | Actualizar contraseña',
    COMMENT_VIEW_TITLE: 'Moovify | Comentario',
    USER_LOGIN: 'Iniciar sesión',
    USER_LOGIN_ALT: 'Iniciar sesión',
    USER_LOGOUT: 'Salir',
    USER_LOGIN_USERNAME: 'Nombre de usuario',
    USER_LOGIN_PASSWORD: 'Contraseña',
    USER_LOGIN_REMEMBERME: 'Recordarme',
    USER_LOGIN_NOACCOUNT: '¿Todavía no tienes una cuenta?',
    USER_LOGIN_RESETPASSWORD: 'Olvidé mi contraseña',
    USER_CREATE_SIGNUP: 'Registro',
    USER_CREATE_SIGNUP_ALT: 'Registrarse',
    USER_CREATE_SIGNUP_ALT2: '¡Regístrate!',
    USER_CREATE_SIGNUPDESC: 'Por favor, completa este formulario para obtener acceso libre a Moovify',
    USER_CREATE_NAME: 'Nombre',
    USER_CREATE_EMAIL: 'Email',
    USER_CREATE_USERNAME: 'Nombre de usuario',
    USER_CREATE_PASSWORD: 'Contraseña',
    USER_CREATE_REPEATPASSWORD: 'Repetir contraseña',
    USER_CREATE_SELECTAVATAR: '¿Deseas seleccionar un avatar? (Opcional)',
    USER_CREATE_SELECTFILE: 'Selecciona un archivo',
    USER_CREATE_NOAVATARSELECTED: 'No se ha seleccionado un avatar',
    USER_CREATE_AVATARSELECTED: 'Avatar seleccionado:',
    USER_CREATE_BUTTON: 'Crear cuenta',
    USER_CREATE_ALREADYACCOUNT: '¿Ya posees una cuenta?',
    USER_PROFILE: 'Mi perfil',
    USER_PROFILE_CREATION_DATE: 'En Moovify desde:',
    USER_EMAILCONFIRM_TITLE: 'Se requiere confirmación de email',
    USER_EMAILCONFIRM_TEXT: 'Para poder crear un post, debes confirmar tu dirección email. Revisa tu casilla de email, te enviamos un Token!',
    USER_EMAILCONFIRM_PROFILE: "Por favor inserta aquí el token que recibiste por email al registrarte. Si no lo tienes, ",
    USER_PROFILE_RESENDEMAIL: 'Reenviar email',
    USER_TOKEN_CONFIRM: "Confirmar",
    USER_TOKEN_ERROR: "Token inválido",
    USER_EMAILCONFIRM_CLOSEMODAL: 'Ok, lo tengo!',
    USER_NAME:'Nombre: {{name}}',
    USER_DESCRIPTION: 'Descripción: {{description}}',
    USER_DESCRIPTION_EMPTY: 'Descripción: Este usuario todavía no posee una descripción',
    USER_DESCRIPTION_PLACEHOLDER: 'Descripción',
    USER_VOTES: 'Votos totales: {{votes}}',
    USER_UPDATE_PASSWORD: 'Nueva contraseña',
    USER_UPDATE_PASSWORD_CHECK: 'Repetir contraseña',
    USER_RESET_PASSWORD_HEADER: "Ingresa tu email para cambiar tu contraseña",
    USER_RESETPASSWORD_VALIDATEDEMAIL: 'Importante: debes validar tu email para poder cambiar la contraseña',
    USER_RESETPASSWORD_SEND: "Enviar email",
    USER_RESETPASSWORD_EMAIL: "Ingresa tu email",
    USER_EDIT_NAME: "Editar tu nombre",
    USER_EDIT_USERNAME: "Editar tu nombre de usuario",
    USER_EDIT_DESCRIPTION: "Editar tu descripción",
    OWNER_DELETED: 'Propietario borrado',
    COMMENT_BY:'Por: {{username}}',
    COMMENT_BY_REMOVED: 'Este usuario ha sido removido',
    COMMENT_META: 'Post: {{title}} - Votos: {{votes}}',
    VOTES_DISPLAY:'- Votos: {{votes}}',
    SEARCH:'Buscar',
    SEARCH_PLACEHOLDER: 'Buscar...',
    ALL_POSTS: 'Todos los posts',
    ALL_COMMENTS:'Todos los comentarios',
    POST_CATEGORIES: 'Tipo de post:',
    POST_AGE: 'Desde:',
    MOVIE_CATEGORY: 'Categoría:',
    MOVIE_DECADE: 'Decada:',
    USER_ROLE: 'Rol:',
    SEARCH_ORDER_BY: 'Ordenar por:',
    POST_TAB_DISPLAY: 'Posts',
    MOVIE_TAB_DISPLAY: 'Películas',
    USER_TAB_DISPLAY: 'Usuarios',
    COMMENT_TAB_DISPLAY: 'Comentarios',
    ANY_TIME: 'Todos los tiempos',
    MOVIE_YEAR: 'Año: {{year}}',
    ALL: 'Todos',
    ANY: 'Cualquiera',
    CATEGORIES: 'Categorías:',
    POST_NOT_FOUND: 'No hay posts que coincidan con estos criterios. Intenta modificarlos o buscar nuevos post en el inicio.',
    MOVIES_NOT_FOUND: 'No hay películas que coincidan con estos criterios. Intenta modificarlos.',
    USERS_NOT_FOUND: 'No hay usuarios que coincidan con estos criterios. Intenta modificarlos',
    COMMENT_NOT_FOUND: 'Todavía no hay comentarios',
    NAVBAR_CREATE_POST: 'Crear post',
    FORM_FIELD_REQUIRED: 'Campo requerido',
    FORM_BAD_CREDENTIALS: 'Usuario o contraseña incorrecta',
    FORM_MIN_LENGTH_ERROR: 'El largo mínimo es de {{ minLen }} carácteres',
    FORM_MAX_LENGTH_ERROR: 'El largo máximo es de {{ maxLen }} carácteres',
    FORM_NAME_PATTERN_ERROR: 'Solo se permiten letras y espacios',
    FORM_EMAIL_PATTERN_ERROR: 'Email inválido',
    FORM_USERNAME_PATTERN_ERROR: "Solo se permiten los siguientes carácteres: letras, numeros, '_' y '#'",
    FORM_PASSWORD_PATTERN_ERROR: "No se permiten espacios en blanco",
    FORM_REPEAT_PASSWORD_ERROR: "Las contraseñas no coinciden",
    FORM_DUPLICATED_FIELD_ERROR: "{{ field }} ya existe, por favor elige otra",
    FORM_AVATAR_SIZE_ERROR: "Tamaño máximo 1MB",
    FORM_TITLE_REQUIRED:"Debes escribir un título",
    FORM_CATEGORY_REQUIRED:"Debes seleccionar una categoría válida",
    FORM_BODY_REQUIRED:"Tu post debe tener cuerpo",
    FORM_MOVIE_MIN_REQUIRED:"Debes seleccionar al menos una película",
    FORM_MOVIE_MAX_REQUIRED:"No puedes seleccionar más de 20 películas",
    POST_CREATE: "Crear nuevo post",
    POST_CREATE_CHOOSE_TITLE: "Elije un título para tu post (Al menos 6 carácteres)",
    POST_CREATE_TITLE_PLACEHOLDER: "Título",
    POST_CREATE_CHOOSE_CATEGORY: "Selecciona una categoría para tu post (Requerido)",
    POST_CREATE_CATEGORY_PLACEHOLDER: "Selecciona una categoría",
    POST_CREATE_WRITE_BODY: "Escribe el cuerpo de tu post aquí (No puede estar vacío)",
    POST_CREATE_BODY_PLACEHOLDER: "Escribe tu post aquí...",
    POST_CREATE_BUTTON: "CREAR",
    POST_CREATE_MOVIES_DISCUSSED: "¿Que películas se discuten en este post?",
    POST_CREATE_MOVIES_CONSTRAINS: "Debes seleccionar al menos una (máximo 20)",
    POST_CREATE_ADD:"AÑADIR",
    POST_CREATE_MOVIES_PLACEHOLDER: "Insertá el nombre de la película que deseas agregar",
    POST_CREATE_TAGS: "Agregá algunas palabras claves que describan el post",
    POST_CREATE_TAGS_CONSTRAINS: "Puedes agregar hasta 5 etiquetas (No más de 50 carácteres)",
    POST_CREATE_TAGS_PLACEHOLDER: "Escribe la etiqueta aquí",
    POST_CREATE_SEND: "ENVIAR",
    POST_ORDER_HOTTEST: "Destacados",
    POST_ORDER_NEWEST: "Últimos",
    UPDATE_AVATAR_MODAL_TITLE: "¿Estás seguro que deseas actualizar tu avatar?",
    UPDATE_AVATAR_MODAL_BODY: "Nombre del archivo: ",
    UPDATE_AVATAR_MODAL_CANCEL: "No",
    UPDATE_AVATAR_MODAL_ACCEPT: "Si",
    UPDATE_INFO_MODAL_CANCEL: "Cancelar",
    UPDATE_INFO_MODAL_ACCEPT: "Aceptar",
    MIN_USER_DISPLAY_META_NAME_VOTES: "Nombre: {{name}} - Votos totales: {{totalVotes}}",
    MOVIE_VIEW_TITLE:'{{title}} ({{year}})',
    MOVIE_POSTER:'poster de {{title}}',
    MOVIE_OVERVIEW:'Sinopsis',
    MOVIE_ORIGINAL_TITLE:'Título original',
    MOVIE_RELEASE_DATE:'Fecha de estreno',
    MOVIE_ORIGINAL_LANGUAGE:'Lenguaje original',
    MOVIE_VOTE_AVERAGE:'Voto promedio de la película',
    MOVIE_POST_ABOUT:'Posts sobre esta película',
    CHANGE_PASSWORD_TITLE: 'Cambiar contraseña',
    RECOVER_POST_DELETED:'Restaurar post borrado',
    RECOVER_COMMENT_DELETED:'Restaurar comentario borrado',
    RECOVER_USER_DELETED:'Restaurar usuario borrado',
    RESTORE:'Restaurar',
    MAIL_FEEDBACK_SUCCESS: 'El mail se ha enviado satisfactoriamente, revisa tu casilla de email!',
    MAIL_FEEDBACK_ERROR: 'Mail no enviado, intenta de nuevo más tarde...',
    UPDATE_PASS_TITLE: 'Cambiar tu contraseña',
    UPDATE_PASS_MSG: 'Inserta aquí tu nueva contraseña',
    UPDATE_PASS_BTN: 'Cambiar contraseña',
    UPDATE_PASS_TOKEN: 'Token',
    UPDATE_PASS_NO_TOKEN: "¿No tienes un Token?",
    UPDATE_PASS_RESEND_EMAIL: "Reenviar email",
    UPDATE_PASS_SUCCESS: "Contraseña cambiada satisfactoriamente, redirigiendo a la página de ingreso...",
    UPDATE_PASS_SUCCESS_ALT: "Contraseña cambiada satisfactoriamente",
    UPDATE_PASS_ERROR: "Un error ha ocurrido, intentalo de nuevo más tarde",
    CONFIRM_EMAIL_FOOTER: "¡Hey! Tienes que confirmar tu email. Para ello,",
    CONFIRM_EMAIL_BTN_MODAL: "Haz click aquí",

    WATCHLIST:'Lista',
    CRITIQUE:"Crítica",
    DEBATE:"Debate",
    NEWS:"Noticias",
    PAST_YEAR:'Último año',
    PAST_MONTH:'Último mes',
    PAST_WEEK:'Última semana',
    PAST_DAY:'Últimas 24hs',
    NEWEST:'Más recientes',
    OLDEST:'Más antiguos',
    HOTTEST:'Destacados',
    TITLE:'Título',
    MOST_POSTS: 'Más posts',
    VOTES: 'Más votos',
    FOLLOWERS:'Más seguidores',
    USERNAME:'Nombre de usuario',
    ACTION:'Acción',
    ADVENTURE:'Aventura',
    ANIMATION: 'Animación',
    COMEDY: 'Comedia',
    CRIME: 'Crimen',
    DOCUMENTARY: 'Documental',
    DRAMA: 'Drama',
    FAMILY: 'Familia',
    FANTASY: 'Fantasía',
    HISTORY: 'Historia',
    HORROR: 'Horror',
    MUSIC: 'Musica',
    MYSTERY: 'Misterio',
    ROMANCE: 'Romance',
    SCIENCE_FICTION: 'Ciencia ficción',
    TV_MOVIE: 'Películas de tv',
    THRILLER: 'Suspenso',
    WAR: 'Guerra',
    WESTERN: 'Lejano oeste',
    USER: 'Usuario',
    ADMIN: 'Administrador',
    PAGINATION_PAGE_SIZE: "Mostrando",
    PROFILE_POST_TAB_DISPLAY: "Tus posts",
    USER_POST_TAB_DISPLAY: "Posts",
    PROFILE_COMMENTS_TAB_DISPLAY: "Tus comentarios",
    USER_COMMENTS_TAB_DISPLAY: "Comentarios",
    PROFILE_BOOK_TAB_DISPLAY: "Tus posts guardados",
    USER_BOOK_TAB_DISPLAY: "Posts guardados",
    PROFILE_FOLLOWED_USERS: "Tus usuarios seguidos",
    USER_FOLLOWED_USERS: "Usuarios seguidos",
    PROFILE_EDIT_DETAILS: "Editar información",
    PROFILE_NO_DESCRIPTION: "Todavía no posees una descripción!",
    USER_NO_DESCRIPTION: "Este usuario no posee una descripción",
    USER_FOLLOW: "Seguir",
    USER_UNFOLLOW: "Dejar de seguir",
    USER_PROMOTE: "Promover",
    USER_PROMOTE_TITLE: "¿Estás seguro que quieres promover a {{username}} a administrador?",
    USER_DELETE: "Eliminar",
    USER_DELETE_TITLE: "¿Estás seguro que quieres eliminar a {{username}}?",
    PAGE_404: "404 - Página no encontrada",
    PAGE_404_1: "Disculpe, la página a la que está intentando acceder no existe.",
    PAGE_404_2: "Podría deberse a que la dirección ya no está disponible o está mal escrita.",
    PAGE_500: "500 - Error Interno del Servidor",
    PAGE_500_1: "Ocurrió un error al procesar la solicitud",
    PAGE_500_2: "Nos disculpamos por el error, puede dirigirse a la pagina principal e intentarlo denuevo",
    PAGE_404_BTN: "Ir a la página principal",
    SIGNOUT_EVERYWHERE: "Cerrar sesión en todas partes",

    COMMENT_VIEW_HEADING: "Comentario del post:",
    SIGN_UP_TO_START_COMMENTING: "Quieres compartir tus ideas? Registrate y empezá a comentar al instante!",
    CONFIRM_EMAIL_TO_START_COMMENTING: "Ya casi terminás, confirmá tu email y comenzá a compartir!",
    CREATE_NEW_ACCOUNT: 'Crear una nueva cuenta',
    GO_TO_PROFILE: 'Ir al perfil',
    COMMENT_REPLIES: 'Respuestas del comentario',
    DELETE_COMMENT_BUTTON: 'Borrar',
    REPLY_COMMENT_BUTTON: 'Responder',
    GO_TO_VIEW_COMMENT_BUTTON: 'Ver comentario',
    EDIT_COMMENT_BUTTON: 'Editar',
    MAIL_AUTHOR_COMMENT_BUTTON: 'Enviar mail',
    COMMENT_VIEW_CREATE_NEW_COMMENT: 'Crear un nuevo comentario',
    CONFIRM_COMMENT_DELETE_MODAL_TITLE: 'Confirmar borrar',
    CONFIRM_COMMENT_DELETE_MODAL_BODY: 'Borrar el comentario "{{commentBody}}"?',
    CONFIRM_COMMENT_DELETE_MODAL_CANCEL_BUTTON: 'Cancelar',
    CONFIRM_COMMENT_DELETE_MODAL_DELETE_BUTTON: 'Borrar',
    DELETED_COMMENT_MESSAGE: 'Este comentario ha sido borrado',
    CONFIRM_GO_TO_COMMENT_VIEW_MODAL_TITLE: 'Yendo a la vista del comentario',
    CONFIRM_GO_TO_COMMENT_VIEW_MODAL_BODY: 'Para poder responderle a este comentario debes ir a su página',
    CONFIRM_GO_TO_COMMENT_VIEW_MODAL_CANCEL_BUTTON: 'Cancelar',
    CONFIRM_GO_TO_COMMENT_VIEW_MODAL_GO_BUTTON: 'Ir a la página',
    COMMENT_REPLY_CANCEL_BUTTON: 'Cancelar',
    COMMENT_REPLY_SEND_BUTTON: 'Enviar',
    CANCEL_COMMENT_EDIT_BUTTON: 'Cancelar',
    SEND_COMMENT_EDIT_BUTTON: 'Enviar',
    WRITE_COMMENT_PLACEHOLDER: 'Escribe tu nuevo comentario aquí...',
    WRITE_REPLY_COMMENT_PLACEHOLDER: "Escribe tu comentario aquí...",

    POST_VIEW_POST_AUTHOR: 'Autor: {{postAuthor}}',
    POST_VIEW_MOVIES: 'Películas',
    POST_VIEW_TAGS: 'Tags',
    POST_EDIT_BUTTON: 'Editar post',
    POST_DELETE_BUTTON: 'Borrar post',
    POST_VIEW_COMMENTS: 'Comentarios',
    CREATION_DATE: ' - Fecha de creación: {{creationDate}} -',
    MOVIE_WITH_SPACER: ' -{{title}}',
    NO_COMMENTS_PLACEHOLDER: 'No hay comentarios para mostrar, escribe el primero!',
    CANCEL_POST_BODY_EDIT: 'Cancelar',
    SEND_POST_BODY_EDIT: 'Editar',
    LAST_EDITED: '- Editado: {{date}}',

    CONFIRM_POST_DELETE_MODAL_TITLE: 'Confirmar borrado',
    CONFIRM_POST_DELETE_MODAL_BODY: 'Borrar el post "{{postTitle}}"?',
    CONFIRM_POST_DELETE_MODAL_CANCEL_BUTTON: 'Cancelar',
    CONFIRM_POST_DELETE_MODAL_DELETE_BUTTON: 'Borrar',

    LOADING: 'Cargando',

    CREATE_ACCOUNT_MODAL_TITLE: "¡Oh! Te gustaría demostrar tu interés?",
    CREATE_ACCOUNT_MODAL_BODY: "Registrate y hazlo al instante!",
    ACTION_WARNING: "Esta accion puede tardar hasta 15 minutos en tener efecto",
    USER_DELETED: "Usuario borrado"
  };
});
