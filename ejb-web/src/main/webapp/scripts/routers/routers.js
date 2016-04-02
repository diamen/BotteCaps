angular.module('bcRouters', [])
	.config(function($routeProvider) {
		
		$routeProvider
			.when("/", { templateUrl: "/ejb-web/views/photo.html", controller: "photoCtrl" })
			.when("/login", { templateUrl: "/ejb-web/views/login.html", controller: "loginCtrl" });
			
		});