var appName = 'ejb-web';

head.load(

	/* Libraries */
    { file: '/' + appName + '/libraries/angular/angular.min.js' },
    { file: '/' + appName + '/libraries/angular-animate/angular-animate.min.js' },
    { file: '/' + appName + '/libraries/angular-cookies/angular-cookies.min.js' },
    { file: '/' + appName + '/libraries/angular-locale/angular-locale_pl-pl.js' },
    { file: '/' + appName + '/libraries/chart/Chart.min.js' },
    { file: '/' + appName + '/libraries/angular-chart/angular-chart.min.js' },
    { file: '/' + appName + '/libraries/ngStorage-master/ngStorage.js' },
    { file: '/' + appName + '/libraries/ui-bootstrap/ui-bootstrap-tpls-2.0.0.min.js' },
    { file: '/' + appName + '/libraries/ui-router/angular-ui-router.min.js' },

    /* Common */
    { file: '/' + appName + '/scripts/common.js' },

    /* Main */
    { file: '/' + appName + '/scripts/app.js' },

    /* Factories */
    { file: '/' + appName + '/scripts/factories/factories.js' },
    { file: '/' + appName + '/scripts/factories/authCacheFactory.js' },
    { file: '/' + appName + '/scripts/factories/validateFactory.js' },

    /* Controllers */
    { file: '/' + appName + '/scripts/controllers/mainCtrl.js' },

    { file: '/' + appName + '/scripts/controllers/addCapCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/addTradeCapsCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/adminCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/capCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/collectCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/countriesCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/editCapCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/editNewsCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/loginCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/modalCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/newsCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/sidebarCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/tradeCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/userStripCtrl.js' },

    /* Directives */
    { file: '/' + appName + '/scripts/directives/directives.js' },
    { file: '/' + appName + '/scripts/directives/tradeImg.js' },

    /* Routers */
    { file: '/' + appName + '/scripts/routers/routers.js' },

    /* Services */
    { file: '/' + appName + '/scripts/services/services.js' },
    { file: '/' + appName + '/scripts/services/markService.js' },
    { file: '/' + appName + '/scripts/services/modalService.js' }
);