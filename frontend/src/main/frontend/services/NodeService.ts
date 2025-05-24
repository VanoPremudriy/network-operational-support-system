import { NodeEndpoint } from 'Frontend/generated/endpoints';
import DetailedNodeResponse from 'Frontend/generated/ru/mirea/cnoss/service/node/dto/DetailedNodeResponse'

const token = localStorage.getItem("token");

export async function getNodeById(id: string): Promise<DetailedNodeResponse> {
  return await NodeEndpoint.getNode(id, token || '');
}