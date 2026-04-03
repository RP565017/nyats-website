export interface Event {
  id: string;
  title: string;
  slug: string;
  description: string;
  shortDescription: string;
  type: string;
  status: string;
  startDate: string;
  endDate: string;
  venue?: {
    name: string;
    address: string;
    city: string;
    state: string;
  };
  pricing?: {
    free: boolean;
    memberPrice: number;
    nonMemberPrice: number;
  };
  maxCapacity: number;
  registeredCount: number;
  coverImageUrl?: string;
  tags?: string[];
  featured: boolean;
  createdAt: string;
}
