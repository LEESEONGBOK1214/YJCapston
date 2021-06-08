import * as S from "./style";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";

const PosMain = ({ shopId }) => {
    const [thisShopId, setThisShopId] = useState("");
    useEffect(() => {
        setThisShopId(shopId);
    });

    return (
        <>
            <S.PosMainWrap>
                <div className="main-container">
                    <body>
                        <Link to={"/pos/" + shopId}>
                            <button className="main-button1">
                                테이블 현황
                            </button>
                        </Link>
                        <Link to={"/emplist/" + shopId}>
                            <button className="main-button2">직원관리</button>
                        </Link>
                        <button
                            className="main-button3"
                            onClick={() =>
                                window.open(
                                    `http://localhost:3000/Shopsocketlist/${shopId}`,
                                    "_blank",
                                    "location = no, toolbars= no, status= no, width = 500, height = 500 , scrollbars = no"
                                )
                            }
                        >
                            예약관리
                        </button>
                        <Link to={"/saleslist/" + shopId}>
                            <button className="main-button4">매출관리</button>
                        </Link>
                        <button
                            onClick={() =>
                                window.open(
                                    `http://localhost:3000/chat/${shopId}`,
                                    "_blank",
                                    "location = no, toolbars= no, status= no, width = 400, height = 500 , scrollbars = no"
                                )
                            }
                        >
                            채팅방가기
                        </button>
                    </body>
                    <footer>
                        <div className="footer">
                            <h3>주문의 민족</h3>
                        </div>
                    </footer>
                </div>
            </S.PosMainWrap>
        </>
    );
};

export default PosMain;
