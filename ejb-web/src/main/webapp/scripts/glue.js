var appName = 'ejb-web';

head.load(
    /* Libraries */
    { file: '/' + appName + '/libraries/angular/angular.min.js' },
    { file: '/' + appName + '/libraries/angular-route/angular-route.min.js' },

    /* Controllers */
    { file: '/' + appName + '/scripts/controllers/photoCtrl.js' },
    
    /* Controllers */
    { file: '/' + appName + '/scripts/directives/directives.js' },
    
    /* Main */
    { file: '/' + appName + '/scripts/app.js' }
);