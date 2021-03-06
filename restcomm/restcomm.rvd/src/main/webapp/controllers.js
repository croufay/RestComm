App.controller('AppCtrl', function ($rootScope, $location) {
	$rootScope.$on("$routeChangeError", function(event, current, previous, rejection) {
        //console.log('on $routeChangeError');
        if ( rejection == "AUTHENTICATION_ERROR" ) {
			console.log("AUTHENTICATION_ERROR");
			$location.path("/login");
		} else {
			$rootScope.rvdError = rejection;
		}
    });
    
    $rootScope.$on("resourceNotFound", function(p1, p2) {
    	console.log("resourceNotFound event caught");
    	$rootScope.rvdError = {message: "The requested resource was not found. Sorry about that."};
    });
    
    $rootScope.$on('$routeChangeStart', function(){
    	$rootScope.rvdError = undefined;
	});
});

var loginCtrl = angular.module('Rvd')
.controller('loginCtrl', ['$scope', '$http', 'notifications', '$location', function ($scope, $http, notifications, $location) {
//	console.log("run loginCtrl ");
	
	$scope.doLogin = function (username, password) {
		$http({	url:'services/auth/login', method:'POST', data:{ username: username, password: password}})
		.success ( function () {
			console.log("login successful");
			$location.path("/home");
		})
		.error( function () {
			console.log("error logging in");
			notifications.put({message:"Login failed", type:"danger"});
		});
	}
}]);


App.controller('homeCtrl', function ($scope, authInfo) {
});

angular.module('Rvd').controller('projectLogCtrl', ['$scope', '$routeParams', 'projectLogService', function ($scope, $routeParams, projectLogService) {
	console.log('in projectLogCtrl');
	$scope.projectName = $routeParams.projectName;
	$scope.logData = '';
	
	function retrieveLog() {
		projectLogService.retrieve().then(function (logData) {$scope.logData = logData;})
	}
	$scope.retrieveLog = retrieveLog;
	
	function resetLog() {
		projectLogService.reset().then(function () {$scope.logData = "";});
	}
	$scope.resetLog = resetLog;
	
	retrieveLog($scope.projectName);
}]);

