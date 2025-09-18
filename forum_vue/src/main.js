import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import axios from "axios";
//导入验证码组件
import SIdentify from "./components/page/Identify";
axios.defaults.baseURL = "http://localhost:8081";
Vue.component("SIdentify", SIdentify);
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$message = ElementUI.Message;
Vue.prototype.$axios = axios;
router.beforeEach((to, from, next) => {
  //console.log(to);
  if (to.meta.logined) {
    next();
  } else {
    if (sessionStorage.getItem("login") === "1") {
      next();
    } else {
      next({ path: "/login" });
    }
  }
});
new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
