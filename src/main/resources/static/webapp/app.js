/**
 * Created by Chaitanya on 12/7/16.
 */
angular.module('ketoMelts', ['ui.router', 'dashboardModule', 'createModule'])
    .config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

    //states and views
        .state('home', {
            url: '/home',
            templateUrl: 'webapp/js/partials/home.html',
            controller:'dashboardController'
        })
        .state('create', {
            url: '/create',
            templateUrl: 'webapp/js/partials/create/create.html',
            controller: 'createController'
        })

    })