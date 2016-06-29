angular.module('bcRouters', [])
	.config(function($routeProvider, $locationProvider) {
		
		$locationProvider.html5Mode(true);
		
		$routeProvider
			.when("/", { templateUrl: "/ejb-web/views/news.html", controller: "newsCtrl" } )
			
			.when("/admin/login", { templateUrl: "/ejb-web/views/login.html", controller: "loginCtrl" } )
			.when("/admin/secure", { templateUrl: "/ejb-web/views/admin.html", controller: "adminCtrl" } )
			.when("/admin/addcap/:country", { templateUrl: "/ejb-web/views/add.cap.html", controller: "addCapCtrl" } )
			
			.when("/news/:id", { templateUrl: "/ejb-web/views/anews.html" } )
			
			.when("/collect", { templateUrl: "/ejb-web/views/collect.html", controller: "collectCtrl" } )
			.when("/collect/:country", { templateUrl: "/ejb-web/views/country.html", controller: "countryCtrl" } )
			.when("/collect/:country/:id", { templateUrl: "/ejb-web/views/cap.html", controller: "capCtrl" } )
			
			.otherwise( { templateUrl: "/ejb-web/views/news.html", controller: "newsCtrl" } );
		});