'use strict';

/* Controllers */

var phonecatApp = angular.module('tagMsg', []);

phonecatApp.controller('RequestListCtrl', function($scope) {
  $scope.requests = [
    {'content': 'nanteuttano?',
	 'tags': 'japanese, english',
     'date': 'some date'},
	 {'content': 'nanteuttano?',
	 'tags': 'japanese, english',
     'date': 'some date'},
	 {'content': 'nanteuttano?',
	 'tags': 'japanese, english',
     'date': 'some date'}

  ];
});
