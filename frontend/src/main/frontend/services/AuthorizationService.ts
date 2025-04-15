import {AuthEndpoint} from "Frontend/generated/endpoints"



export const getAuthToken = async () => {
  const response = await AuthEndpoint.auth();
  if (response.token) {
    localStorage.setItem("token", response.token);
    return true
  } else {
    console.error("Ошибка: токен отсутствует!");
    return false;
  }
};