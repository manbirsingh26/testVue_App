/*import { createWebHistory, createRouter } from "vue-router";
import StudentLogin from "../components/StudentLogin.vue";
//import RegisteredCourseList from "../components/RegisteredCourseList.vue";
//import CourseList from "../components/CourseList";
const routes = [
  {
    path: "/",
    alias: "/login",
    name: "login",
    component: StudentLogin
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;*/

import { createRouter, createWebHistory } from 'vue-router';
import StudentLogin from '../components/StudentLogin.vue';
import HelloWorld from '../views/HelloWorld.vue';
const routes = [
  {
    path: '/',
    name: 'Login',
    component: StudentLogin
  },
  {
    path: '/helloworld',
    name: 'HelloWorld',
    component: HelloWorld
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes
});
export default router;


