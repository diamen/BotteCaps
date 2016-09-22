angular.module('bcDirectives')
	.directive("markButton", function() {
		return {
			restrict: 'E',
			link: function(scope, element, attrs) {
				element.css('width', 'inherit');
				
				element.bind("click", function(clickEvent) {
					var btn = element.children();
					if(btn.hasClass("btn-primary")) {
						btn.removeClass("btn-primary");
						btn.addClass("btn-danger");
					} else{
						btn.removeClass("btn-danger");
						btn.addClass("btn-primary");
					}
				});
			},
			template: '<button style="width: inherit" type="submit" class="btn btn-primary">Zaznacz</button>'
		};
	});