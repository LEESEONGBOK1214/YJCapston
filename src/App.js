import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import React from "react";
import MenuRegisterFormContainer from "./containers/MenuRegisterFormContainer";
import LoginContainer from "./containers/Login/LoginContainer";
import MainContainer from "./containers/Main/MainContainer";
import MypageContainer from "./containers/MyPage/MyPageContainer";
import RegisterContainer from "./containers/Register/RegisterContainer";
import ShopContainer from "./containers/Shop/ShopContainer";
import { useEffect, useState } from "react";
import { MenuReadContainer } from "./containers/MenuRead/MenuReadContainer";
import MenuListContainer from "./containers/MenuList/MenuListContainer";
import ShopInfoContainer from "./containers/ShopInfo/ShopInfoContainer";
import MyShopContainer from "./containers/MyShop/MyShopContainer";
import ShoplistContainer from "./containers/Shoplist/ShoplistContainer";
import ShopcontentContainer from "./containers/shopcontent/shopcontentcontainer";
import ShopOrderContainer from "./containers/ShopOrder/ShopOrderContainer";
import PaymentContainer from "./containers/PaymentDone/PaymentDoneContainer";
import AddReviewContainer from "./containers/AddReview/AddReviewContainer";

const App = () => {
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    const accesstoken = sessionStorage.getItem("access_token");

    if (accesstoken) {
      setIsLogin(true);
    }
  }, []);

  const handleLogin = () => {
    setIsLogin(true);
    alert("회원님 반가워요!");
  };

  const handleLogout = () => {
    setIsLogin(false);
    sessionStorage.removeItem("access_token");
    alert("로그아웃이 완료 되었습니다.");
  };
  return (
    <>
      <Router>
        <Switch>
          <Route path="/login" component={LoginContainer} />
          <Route path="/register" component={RegisterContainer} />
          <Route path="/mypage" component={MypageContainer} />
          <Route path="/shop" component={ShopContainer} />
          <Route path="/MyShop" component={MyShopContainer} />
          <Route component={MenuListContainer} path="/menuList/:shopId" />
          <Route component={MenuRegisterFormContainer} path="/create/:shopId" />
          <Route path="/ShopInfo/:shopId" component={ShopInfoContainer} />
          <Route component={MenuReadContainer} path='/menu/:menuId' />
          <Route component={AddReviewContainer} path='/addreview/:shopId/:orderId' />
          <Route
            path="/shopcontent/:shopId"
            component={() => (
              <ShopcontentContainer
                isLogin={isLogin}
                handleLogin={handleLogin}
                handleLogout={handleLogout}
              />
            )}
          ></Route>
          <Route
            path="/shoplist"
            component={() => (
              <ShoplistContainer
                isLogin={isLogin}
                handleLogin={handleLogin}
                handleLogout={handleLogout}
              />
            )}
          />
          <Route
            path="/shoporder"
            component={() => (
              <ShopOrderContainer
                isLogin={isLogin}
                handleLogin={handleLogin}
                handleLogout={handleLogout}
              />
            )}
          ></Route>
          <Route
            path="/paymentDone"
            component={() => (
              <PaymentContainer
                isLogin={isLogin}
                handleLogin={handleLogin}
                handleLogout={handleLogout}
              />
            )}
          ></Route>
          <Route
            path="/"
            component={() => (
              <MainContainer
                isLogin={isLogin}
                handleLogin={handleLogin}
                handleLogout={handleLogout}
              />
            )}
          />
        </Switch>
      </Router>
    </>
  );
};

export default App;
