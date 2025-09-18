import Vue from "vue";
import VueRouter from "vue-router";
import AddView from "../views/AddView.vue";
import PostsView from "../views/PostsView.vue";
import PostView from "../views/PostView.vue";
import UpdatePostView from "../views/UpdatePostView.vue";
import LoginView from "../views/LoginView.vue";
import Home from "../components/page/Home.vue";
import RegisterView from "../views/RegisterView.vue";
import CollectionView from "../views/CollectionPostsView.vue";
import MyPosts from "../views/MyPostsView.vue";
import MyMessage from "../views/MyMessageView.vue";
import UpdatePassword from "../views/UpdatePasswordView.vue";
import MyComments from "../views/MyCommentsView.vue";
import PostsByLabelView from "../views/PostsByLabelView.vue";
import BlankView from "../views/BlankView";
Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    meta: { logined: true },
    component: LoginView,
  },
  {
    path: "/register",
    meta: { logined: true },
    component: RegisterView,
  },
  {
    path: "/main",
    component: Home,
    meta: { logined: false },
    children: [
      {
        path: "/index",
        component: BlankView,
      },
      {
        path: "/add",
        component: AddView,
      },
      {
        path: "/collection",
        component: CollectionView,
      },
      {
        path: "/post",
        component: PostsView,
      },
      {
        path: "/postsByLabel",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelGame",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelEntertainment",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelComic",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelNovel",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelTrval",
        component: PostsByLabelView,
      },
      {
        path: "/postsByLabelPolitics",
        component: PostsByLabelView,
      },
      {
        path: "/message",
        component: MyMessage,
      },
      {
        path: "/myposts",
        component: MyPosts,
      },
      {
        path: "/updatepwd",
        component: UpdatePassword,
      },
      {
        path: "/detailpost",
        component: PostView,
      },
      {
        path: "/updatepost",
        component: UpdatePostView,
      },
      {
        path: "/mycomment",
        component: MyComments,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
export default router;
