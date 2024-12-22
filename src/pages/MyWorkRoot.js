import React, { useState, useEffect, useCallback, useMemo } from "react";
import { FaCarSide } from "react-icons/fa";
import { GoOrganization } from "react-icons/go";
import { useNavigate } from "react-router-dom";

function MyWorkRoot() {
  const navigate = useNavigate();
  const [isMoving, setIsMoving] = useState(false);
  const [carPosition, setCarPosition] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentCompany, setCurrentCompany] = useState(null);
  const [visitedCompanies, setVisitedCompanies] = useState([]);
  const [journeyComplete, setJourneyComplete] = useState(false);
  const [messageModal, setMessageModal] = useState(false);

  const companiesList = [
    {
      id: 1,
      name: "Infosys",
      description:
        "Infosys is a global leader in next-generation digital services and consulting.",
      myRole: "Full Stack Developer",
    },
    {
      id: 2,
      name: "TCS",
      description:
        "Tata Consultancy Services, a leading global IT services and consulting company.",
      myRole: "Full Stack Developer",
    },
    {
      id: 3,
      name: "Google",
      description:
        "A global technology leader known for innovation and digital services.",
      myRole: "Full Stack Developer",
    },
    {
      id: 4,
      name: "Amazon",
      description:
        "A multinational technology company focusing on e-commerce, cloud computing, and AI.",
      myRole: "Full Stack Developer",
    },
  ];

  const roadLength = companiesList.length * 18; 

  useEffect(() => {
    if(journeyComplete === true){
      navigate("/work")
    }
  });

  

  const startCar = useCallback(() => {
    setIsMoving(true);
  }, []);

  const handleOkClick = useCallback(() => {
    setMessageModal(false);
    setIsMoving(true);
  }, []);

  useEffect(() => {
    let moveInterval;
    if (isMoving) {
      moveInterval = setInterval(() => {
        setCarPosition((prev) => {
          if (prev < roadLength) {
            if (prev % 15 === 0 && prev !== 0) {
              const companyIndex = prev / 15 - 1;
              const company = companiesList[companyIndex];
              setCurrentCompany(company);
              setIsModalOpen(true);
              setIsMoving(false);
              clearInterval(moveInterval);
            }
            return prev + 1;
          } else {
            clearInterval(moveInterval);
            setJourneyComplete(true);
            return prev;
          }
        });
      }, 120);
    }

    return () => {
      if (moveInterval) {
        clearInterval(moveInterval);
      }
    };
  }, [isMoving]);

  const handleVisitDecision = useCallback(
    (decision) => {
      if (decision === "Visit" && currentCompany) {
        setVisitedCompanies((prev) => [...prev, currentCompany]);
        setMessageModal(true);
        setIsModalOpen(false);
        setIsMoving(false);
      } else {
        setIsModalOpen(false);
        setIsMoving(true);
      }
    },
    [currentCompany]
  );

  const road = useMemo(
    () =>
      Array.from({ length: roadLength }, (_, index) => {
        if (index === carPosition) {
          return (
            <span key={index}>
              <FaCarSide className="text-4xl mt-7 text-blue-500 animate-bounce" />
            </span>
          );
        }
        if (index % 15 === 0 && index !== 0) {
          return (
            <span key={index}>
              <GoOrganization className="text-6xl text-green-500 animate-pulse" />
            </span>
          );
        }
        return (
          <span className="mt-10" key={index}>
            {" "}
            _{" "}
          </span>
        );
      }),
    [carPosition]
  );

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-r from-gray-200 to-gray-300 px-4">
      <div className="flex flex-col items-center justify-center absolute bottom-5">
        <div className="text-2xl font-bold mb-4">MyWorkRoot</div>

        <div className="flex items-center justify-center mb-6">
          <div className="text-lg font-semibold mr-4">Road to Success:</div>
          <div className="flex">{road}</div>
        </div>

        <button
          onClick={startCar}
          className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition duration-300 mb-4"
          disabled={isMoving || journeyComplete}
        >
          {journeyComplete ? "Journey Complete" : "Start"}
        </button>
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-gray-600 bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-80">
            <div className="text-xl font-semibold mb-4">
              Do you want to visit {currentCompany.name}?
            </div>
            <div className="flex justify-around">
              <button
                onClick={() => handleVisitDecision("Visit")}
                className="bg-green-500 text-white py-2 px-4 rounded-lg hover:bg-green-600"
              >
                Visit
              </button>
              <button
                onClick={() => handleVisitDecision("Skip")}
                className="bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600"
              >
                Skip
              </button>
            </div>
          </div>
        </div>
      )}
      <div className="flex flex-col items-center justify-center absolute top-24 mt-5 h-auto w-[20%]">
        {messageModal && (
          <div className=" inset-0 flex items-center justify-center absolute top-5 bg-gray-600 bg-opacity-50">
            <div className="bg-white p-6 rounded-lg shadow-lg w-80 text-center">
              <h5 className="text-lg font-semibold">{currentCompany.name}</h5>
              <h6 className="text-sm font-medium">{currentCompany.myRole}</h6>
              <p className="text-gray-600">{currentCompany.description}</p>
              <p className="mt-4 text-sm text-gray-500">
                Car will continue shortly...
              </p>

              <button
                onClick={handleOkClick}
                className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600"
              >
                ok
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default MyWorkRoot;
