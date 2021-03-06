angular.module('bcDirectives')

	.directive("tradeImg", function() {
		return {
			restrict: 'E',
			scope: {
				source: '@'
			},
			link: function(scope, element) {

				element.css('width', '150px');
				element.css('height', '150px');
				element.css('display', 'inline-block');
				element.css('padding', '5px');

				element.children().bind('load', function() {
					var height = element.children()[0].height;
					var width = element.children()[0].width;

					if(width >= height) {
						element.children().css('width', '140px');
						element.children().css('height', 'auto');

						element.css('white-space', 'nowrap');

						element.prepend("<span style='height: 100%; display: inline-block; vertical-align: middle;'></span>");
					} else {
						element.children().css('width', 'auto');
						element.children().css('height', '140px');

						element.css('text-align', 'center');
					}

				});

			},
			template: "<img src={{source}}></img>"
		};
	})