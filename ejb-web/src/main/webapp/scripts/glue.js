var appName = 'ejb-web';

head.load(
    
	/* Libraries */
    { file: '/' + appName + '/libraries/angular/angular.min.js' },
    { file: '/' + appName + '/libraries/angular-route/angular-route.js' },
    { file: '/' + appName + '/libraries/angular-cookies/angular-cookies.min.js' },
    { file: '/' + appName + '/libraries/angular-locale/angular-locale_pl-pl.js' },
    
    /* Main */
    { file: '/' + appName + '/scripts/app.js' },
    
    /* Controllers */
    { file: '/' + appName + '/scripts/controllers/mainCtrl.js' },
    
    { file: '/' + appName + '/scripts/controllers/adminCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/anewsCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/capCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/collectCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/countryCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/loginCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/newsCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/photoCtrl.js' },
    
    /* Directives */
    { file: '/' + appName + '/scripts/directives/directives.js' },
    
    /* Factories */

    
    /* Routers */
    { file: '/' + appName + '/scripts/routers/routers.js' },
    
    /* Services */
    { file: '/' + appName + '/scripts/services/services.js' }
);