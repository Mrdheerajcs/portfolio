import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children, allowedRoles }) => {
  const role = localStorage.getItem("role");

  if (!role) {
    return <Navigate to="/" />;
  }

  if (allowedRoles.includes(role)) {
    return children;
  } else {
    return <Navigate to="/" />;
  }
};

export default PrivateRoute;
