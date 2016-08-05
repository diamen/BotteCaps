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
				
				.state("news.add", {
					url: "/add",
					views: {
						"newsView@news": {
							controller: "newsCtrl",
							templateUrl: "/ejb-web/views/news/addnews.html"
							}
					}
				});

});