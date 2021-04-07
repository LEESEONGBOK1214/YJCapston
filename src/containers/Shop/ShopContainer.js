import axios from "axios";
import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import OwnerNavbar from "../../components/OwnerMenubar/OwnerNavbar";
import Header from "../../components/Header/Header";
import Shop from "../../components/Shop/Shop";
import { apiDefault } from "../../lib/client";
import { postShop } from "../../lib/Shop";
import { getLocation} from "../../lib/Register";

const ShopContainer = () => {
    const history = useHistory();
    const [id, setId] = useState("");
    const [shopname, setShopname] = useState("");
    const [intro, setIntro] = useState("");
    const [open_time, setOpen_time] = useState("");
    const [close_time, setClose_time] = useState("");
    const [address, setAddress] = useState("");
    const [address1, setAddress1] = useState("");
    // const [is_rs_pos, setIs_rs_pos] = useState("");
    const [category, setCategory] = useState("");
    const [img_url, setImg_url] = useState("");
    const [keyword, setKeyword] = useState("");
    const [roadAddr, setRoadAddr] = useState("주소를 입력하세요.");
    const [showLocation, setShowLocation] = useState([
        {
            roadAddr: "",
        },
    ]);
    const [modal, setModal] = useState(false);

    // const [modal, setModal] = useState(false);

    // const openmodal = () => {
    //     setModal(true);
    //     console.log("true");
    // };
    // const closemodal = () => {
    //     setModal(false);
    //     console.log("false");
    // };

    // const handleIs_rs_pos = (e) => {
    //     const value = e.target.value;
    //     setInputStatus(value);
    // }
    
    const openmodal = () => {
        setModal(true);
        console.log("true");
    };
    const closemodal = () => {
        setModal(false);
        console.log("false");
    };

    const handleId = (e) => {
        const value = e.target.value;
        setId(value);
    };

    const handleShopname = (e) => {
        const value = e.target.value;
        setShopname(value);
    };

    const handleIntro = (e) => {
        const value = e.target.value;
        setIntro(value);
        console.log(value);
    };

    const handleOpen_time = (e) => {
        const value = e.target.value;
        setOpen_time(value);
    };
    const handleClose_time = (e) => {
        const value = e.target.value;
        setClose_time(value);
    };
    const handleAddress = (e) => {
        const value = e.target.value;
        setAddress(value);
    };
    const handleAddress1 = (e) => {
        const value = e.target.value;
        setAddress1(value);  
    };
    // const handleIs_rs_pos = (e) => {
    //     const value = e.target.value;
    //     setIs_rs_pos(value);
    //     console.log(is_rs_pos);
    // };
    const handleCategory = (e) => {
        const value = e.target.value;
        setCategory(value);
    };
    const handleImg_url = (e) => {
        const files = e.target.files[0];
        setImg_url(files);
    };

    const handleRoadAddr = (roadAddr) => {
        setRoadAddr(roadAddr);
        closemodal();
    };
    const handleKeyword = (e) => {
        const value = e.target.value;
        setKeyword(value);
    };
    
    const shop_v1 = async () => {
        const formData = new FormData();
        formData.append("id",id);
        formData.append("name",shopname);
        formData.append("intro",intro);
        formData.append("openTime",open_time);
        formData.append("closeTime",close_time);
        formData.append("address",address);
        formData.append("addressDetail",address1);
        formData.append("category",category);
        formData.append("img",img_url);
        console.log(formData);
    

        const res = await apiDefault().post("/shop",
           
            formData,
          
        {
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("access_token")}`,
                "content-type": "multipart/form-data",
            },
        }
        ).then((res) => {
            history.push("/myShop")
        })
        .catch((err) => {
            alert("Err");
        });
        console.log(res);
    }

    const search = () => {
        getLocation(keyword)
            .then((res) => {
                const a = JSON.parse(res.data);
                const regilo = a.results.juso.map((j) => {
                    return {
                        roadAddr: j.roadAddr,
                    };
                });
                setShowLocation(regilo);
            })
            .catch((err) => {
                alert(err);
            });
    };


    const handleComplete = (data) => {
        let fullAddress = data.address;
        let extraAddress = "";

        if (data.addressType === "R") {
            if (data.bname !== "") {
                extraAddress += data.bname;
            }
            if (data.buildingName !== "") {
                extraAddress +=
                    extraAddress !== ""
                        ? `, ${data.buildingName}`
                        : data.buildingName;
            }
            fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
        }

        handleRoadAddr(fullAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
    };

    return (
        <>
        <Header/>

        {/* <OwnerNavbar /> */}
         
        <Shop
            id={id}
            handleId={handleId}
            shopname={shopname}
            handleShopname={handleShopname}
            intro={intro}
            handleIntro={handleIntro}
            open_time={open_time}
            handleOpen_time={handleOpen_time}
            close_time={close_time}
            handleClose_time={handleClose_time}
            address={address}
            handleAddress={handleAddress}
            address1={address1}
            handleAddress1={handleAddress1}
            // is_rs_pos={is_rs_pos}
            // handleIs_rs_pos={handleIs_rs_pos}
            category={category}
            handleCategory={handleCategory}
            img_url={img_url}
            handleImg_url={handleImg_url}
            shop_v1={shop_v1}
            showLocation={showLocation}
            roadAddr={roadAddr}
            handleComplete={handleComplete}
            search={search}
            modal={modal}
            openModal={openmodal}
            closeModal={closemodal}
            handleKeyword={handleKeyword}
        />
        </>
    );
};

export default ShopContainer;
