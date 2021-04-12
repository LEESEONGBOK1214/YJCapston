import { Link } from "react-router-dom";
import React from "react";
import * as S from "./style";

const ShopInfo = ({
    shopId,
    id,
    name,
    intro,
    openTime,
    closeTime,
    category,
    address,
    addressDetail,
    isRsPos,
    shopName,
    handleShopName,
    // handleId,
    shopIntro,
    handleShopIntro,
    shopOpenTime,
    handleShopOpenTime,
    shopClostTime,
    handleShopCloseTime,
    shopAddress,
    handleShopAddress,
    shopCategory,
    handleShopCategory,
    shopAddressDetail,
    handleShopAddressDetail,
    shopIsRsPos,
    handleShopIsRsPos,
    Shop_v1,
}
) => {

        // console.log(shopname.id);
    return(
        <>
        <S.InfoWrap>
            <div className="total-body">
                <div className="title">매장 정보</div>
            <div className="label">
                사업자아이디
            </div>
            <input
                type="text"
                value={id}
                className="input-box"
                disabled
            />
            <div className="label">
                매장이름
            </div>
            <input
                type="text"
                id="name"
                placeholder={name}
                onChange={handleShopName}
                // value={name}
                value={shopName}
                className="input-box"
                // disabled
            />
            <div className="label">
                매장소개
            </div>
            <textarea
            className="input-box-area"
            fow="10"
            placeholder={intro}
            id="intro"
            value={shopIntro}
            onChange={handleShopIntro}
            className="input-box-area"
            />
            <div className="label">
                오픈시간
            </div>
            <input 
                type="text"
                id="open_time"
                placeholder={openTime}
                onChange={handleShopOpenTime}
                className="input-box"
                value={shopOpenTime}
            />
            <div className="label">
            마감시간
            </div>
            <input
                type="text"
                id="close_time"
                placeholder={closeTime}
                onChange={handleShopCloseTime}
                className="input-box"
                value={shopClostTime}
            />
            <div className="label">
            카테고리
            </div>
            <select
                id="category"
                // placeholder={category}
                onChange={handleShopCategory}
                // value={shopCategory}
                value={category}
                className="select"
                >
                <option value="" disable={true}>선택</option>
                <option value="한식">한식</option>
                <option value="일식">일식</option>
                <option value="중식">중식</option>
                <option value="고기">고기</option>
                <option value="분식">분식</option>
                <option value="술집">술집</option>
                <option value="패스트푸드">패스트푸드</option>
                <option value="찜.탕">찜.탕</option>
                <option value="카페.디저트">카페.디저트</option>    

            </select>
            <div className="label">
            매장주소
            </div>
            <input
                type="text"
                id="address"
                placeholder={address}
                onChange={handleShopAddress}
                // value={address}
                value={shopAddress}
                className="input-box"
            />
            <div className="label">
             매장상세주소
            </div>
            <input
                type="text"
                id="addressDetail"
                placeholder={addressDetail}
                onChange={handleShopAddressDetail}
                // value={addressDetail}
                value={shopAddressDetail}
                className="input-box"
            />
            <div className="label">
            예약가능여부
            </div>
            <span
                // style={{
                //     fontSize:"20px",
                //     paddingTop:"20px",
                // }}
            >
            가능
            <input 
                type="radio"
                id="is_rs_pos1"
                name="is_rs_pos"
                // placeholder={isRsPos ="Y"}
            
                value={isRsPos = "Y"}
                className="input-check"
                // onChange={handleIs_rs_pos}
            />
            </span>
            <span className="info-11"
                // style={{
                //     fontSize:"20px",
                //     paddingTop:"20px",
                // }}
            >
            불가능
            <input
                type="radio"
                id="is_rs_pos2"
                name="is_rs_pos"
                // placeholder={isRsPos ="N"}
                value={isRsPos = "N"}
                className="input-check"
                // onChange={handleIs_rs_pos}
            />
            </span>

        <div className="button-box">
            <button onClick={Shop_v1} className="button3">수정</button>
            <Link to="/myshop">
            <button className="button4" >취소</button>
            </Link>
        </div> 

        </div>
        </S.InfoWrap>
        </>
    );
}

export default ShopInfo;