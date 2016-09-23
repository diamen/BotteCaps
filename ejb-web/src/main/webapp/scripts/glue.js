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
    { file: '/' + appName + '/scripts/factories/persistFactory.js' },
    { file: '/' + appName + '/scripts/factories/validateFactory.js' },

    /* Filters */
    { file: '/' + appName + '/scripts/filters/filters.js' },
    { file: '/' + appName + '/scripts/filters/toPlCountry.js' },

    /* Controllers */
    { file: '/' + appName + '/scripts/controllers/mainCtrl.js' },

    { file: '/' + appName + '/scripts/controllers/addCapCtrl.js' },
    { file: '/' + appName + '/scripts/controllers/aboutCtrl.js' },
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

    /* Directives */
    { file: '/' + appName + '/scripts/directives/directives.js' },
    { file: '/' + appName + '/scripts/directives/loading.js' },
    { file: '/' + appName + '/scripts/directives/tradeImg.js' },
    { file: '/' + appName + '/scripts/directives/uploadFile.js' },
    { file: '/' + appName + '/scripts/directives/fileread.js' },
    { file: '/' + appName + '/scripts/directives/markButton.js' },
    { file: '/' + appName + '/scripts/directives/fadeIn.js' },
    { file: '/' + appName + '/scripts/directives/isAdmin.js' },

    /* Routers */
    { file: '/' + appName + '/scripts/routers/routers.js' },

    /* Services */
    { file: '/' + appName + '/scripts/services/services.js' },
    { file: '/' + appName + '/scripts/services/markService.js' },
    { file: '/' + appName + '/scripts/services/modalService.js' },
    { file: '/' + appName + '/scripts/services/entityConverter.js' },
    { file: '/' + appName + '/scripts/services/language.js' },
    { file: '/' + appName + '/scripts/services/capMover.js' },
    { file: '/' + appName + '/scripts/services/shareData.js' },
    { file: '/' + appName + '/scripts/services/base64Service.js' },
    { file: '/' + appName + '/scripts/services/restService.js' },
    { file: '/' + appName + '/scripts/services/ngsrcConvertService.js' }
);