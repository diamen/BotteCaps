angular.module('bcRouters', [])
	.config(function($stateProvider, $urlRouterProvider) {
		
		$urlRouterProvider.otherwise("/news");
		
		$stateProvider
		
			.state("news", {
				url: "/news",
				views: {
					'': { templateUrl: "/ejb-web/views/main/partial-news.html" },
					"newsView@news": {
						controller: "newsCtrl",
						templateUrl: "/ejb-web/views/news/news.html"
						},
					 "sidebarView@news": {
						templateUrl: "/ejb-web/views/main/sidebar.html"
					 	}
					}
				})
				
				.state("news.detail", {
					url: "/:id",
					views: {
						"newsView@news": {
							controller: "anewsCtrl",
							templateUrl: "/ejb-web/views/news/anews.html"
							}
					}
				});
		
//		$locationProvider.html5Mode(true);
//		
//		$routeProvider
//			.when("/", { templateUrl: "/ejb-web/views/main/main.html" } )
//			
//			.when("/admin/login", { templateUrl: "/ejb-web/views/login.html", controller: "loginCtrl" } )
//			.when("/admin/secure", { templateUrl: "/ejb-web/views/admin.html", controller: "adminCtrl" } )
//			.when("/admin/addcap/:country", { templateUrl: "/ejb-web/views/add.cap.html", controller: "addCapCtrl" } )
//			
//			.when("/news/:id", { templateUrl: "/ejb-web/views/anews.html" } )
//			
//			.when("/collect", { templateUrl: "/ejb-web/views/collect.html", controller: "collectCtrl" } )
//			.when("/collect/:country", { templateUrl: "/ejb-web/views/collect.html", controller: "collectCtrl" } )
//			.when("/collect/:country/:id", { templateUrl: "/ejb-web/views/cap.html", controller: "capCtrl" } )
//			
//			.otherwise( { templateUrl: "/ejb-web/views/main/news.html" } );
});