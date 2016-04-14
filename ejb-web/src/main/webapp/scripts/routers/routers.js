angular.module('bcRouters', [])
	.config(function($routeProvider, $locationProvider) {
		
		$locationProvider.html5Mode(true);
		
		$routeProvider
			.when("/", { templateUrl: "/ejb-web/views/photo.html", controller: "photoCtrl" } )
			.when("/prot/login", { templateUrl: "/ejb-web/views/login.html", controller: "loginCtrl" } )
			.when("/admin/login", { templateUrl: "/ejb-web/views/admin.html", controller: "adminCtrl" } )
			.otherwise( { templateUrl: "/ejb-web/views/photo.html", controller: "photoCtrl" } );
			
		});